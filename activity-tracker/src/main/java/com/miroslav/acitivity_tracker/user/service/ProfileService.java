package com.miroslav.acitivity_tracker.user.service;

import com.miroslav.acitivity_tracker.role.model.Role;
import com.miroslav.acitivity_tracker.role.repository.RoleRepository;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.ProfileMapper;
import com.miroslav.acitivity_tracker.user.dto.ProfileResponse;
import com.miroslav.acitivity_tracker.user.dto.UserRequest;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import com.miroslav.acitivity_tracker.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final UserContext userContext;
    private final ProfileMapper profileMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    //TODO create methods
    //TODO create tests

    public Profile getProfile(Integer profileId){
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
    }

    public Profile getProfileByMail(String email){
        return profileRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
    }

    public ProfileResponse getOwnProfile(){
        return profileMapper.toResponse(
                profileRepository.findById(
                        userContext.getAuthenticatedUser().getUserId()
                )
                        .orElseThrow(() -> new EntityNotFoundException("Profile not found"))
        );
    }


    //TODO change roles impl
     public Integer createProfile(UserRequest request){
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("USER was not initialized"));
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        Profile profile = Profile.builder()
                .profileId(user.getUserId())
                .user(user)
                .build();
        return profileRepository.save(profile).getProfileId();
    }

    public Integer disableAccount(Integer profileId){
        User user = userRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setAccountLocked(true);
        user.setEnabled(false);

        return userRepository.save(user).getUserId();
    }

//    public ProfileResponse updateAccount(Integer profileId, UserRequest request){
//        User user = userRepository.findById(profileId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        profileMapper.updateToEntity(user, request);
//
//        return
//    }

    //TODO test
    public String deleteAccount(Integer profileId){
        profileRepository.deleteById(profileId);
        userRepository.deleteById(profileId);

        return "User deleted successfully";
    }

    public String deleteOwnAccount(){
        userRepository.deleteById(userContext.getAuthenticatedUser().getUserId());

        return "User deleted successfully";
    }


}
