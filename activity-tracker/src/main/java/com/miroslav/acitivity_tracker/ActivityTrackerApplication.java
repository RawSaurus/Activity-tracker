package com.miroslav.acitivity_tracker;

import com.miroslav.acitivity_tracker.role.Role;
import com.miroslav.acitivity_tracker.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ActivityTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivityTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
											   RoleRepository roleRepository){
		return args -> {
			Role role = roleRepository.save(Role.builder()
					.name("USER")
					.build());
		};
	}

}
