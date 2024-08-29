package com.miroslav.acitivity_tracker.auth.service;

import com.miroslav.acitivity_tracker.auth.dto.AuthenticationRequest;
import com.miroslav.acitivity_tracker.auth.dto.AuthenticationResponse;
import com.miroslav.acitivity_tracker.auth.dto.RegistrationRequest;
import com.miroslav.acitivity_tracker.email.EmailService;
import com.miroslav.acitivity_tracker.email.EmailTemplateName;
import com.miroslav.acitivity_tracker.role.model.Role;
import com.miroslav.acitivity_tracker.role.repository.RoleRepository;
import com.miroslav.acitivity_tracker.security.JwtService;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.Token;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import com.miroslav.acitivity_tracker.user.repository.TokenRepository;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("{application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException{
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("USER was not initialized"));
        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        Profile profile = Profile.builder()
                .profileId(user.getUserId())
                .user(user)
                .build();
        profileRepository.save(profile);
        sendValidationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        HashMap<String, Object> claims = new HashMap<>();
        User user = (User)auth.getPrincipal();
        claims.put("fullName", user.getFullName());
        String jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send");
        }
        User user = userRepository.findById(savedToken.getUser().getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private void sendValidationEmail(User user) throws MessagingException{
        String newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(User user){
        String generatedToken = generatedActivationCode(6);
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generatedActivationCode(int length){
        String characters = "0123456789";
        StringBuilder sBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i<length; i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            sBuilder.append(characters.charAt(randomIndex));
        }
        return sBuilder.toString();
    }
}
