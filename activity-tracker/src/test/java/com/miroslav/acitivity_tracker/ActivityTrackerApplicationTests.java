package com.miroslav.acitivity_tracker;

import com.miroslav.acitivity_tracker.activity.ActivityRepositoryTest;
import com.miroslav.acitivity_tracker.auth.dto.AuthenticationRequest;
import com.miroslav.acitivity_tracker.auth.dto.RegistrationRequest;
import com.miroslav.acitivity_tracker.auth.service.AuthenticationService;
import com.miroslav.acitivity_tracker.role.model.Role;
import com.miroslav.acitivity_tracker.role.repository.RoleRepository;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootTest
class ActivityTrackerApplicationTests {

	@Test
	void contextLoads() {
	}

}
