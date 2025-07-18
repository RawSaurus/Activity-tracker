package com.miroslav.acitivity_tracker.util.dataloader;

import com.miroslav.acitivity_tracker.auth.dto.AuthenticationRequest;
import com.miroslav.acitivity_tracker.auth.dto.RegistrationRequest;
import com.miroslav.acitivity_tracker.auth.service.AuthenticationService;
import com.miroslav.acitivity_tracker.role.model.Role;
import com.miroslav.acitivity_tracker.role.repository.RoleRepository;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    @Profile("!test")
    public CommandLineRunner commandLineRunner(
            RoleRepository roleRepository,
            UserRepository userRepository,
            AuthenticationService service){
        return args -> {
            if(!roleRepository.existsById(1) && !roleRepository.existsById(2)) {
                Role role = roleRepository.save(Role.builder()
                        .name("USER")
                        .build());
                Role adm = roleRepository.save(Role.builder()
                        .name("ADMIN")
                        .build());

                RegistrationRequest administrator = RegistrationRequest.builder()
                        .firstname("admin")
                        .lastname("admin")
                        .email("admin@gmail.com")
                        .password("asdfasdf")
                        .build();
                service.register(administrator);
                User user = userRepository.findById(1)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                user.setEnabled(true);
                user.setRoles(List.of(adm, role));
                userRepository.save(user);
                var admin = AuthenticationRequest.builder()
                        .email("admin@gmail.com")
                        .password("asdfasdf")
                        .build();
                System.out.println("Token :" + service.authenticate(admin).getToken());
            }
        };
    }
}
