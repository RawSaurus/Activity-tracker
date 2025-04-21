package com.miroslav.acitivity_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = RepositoryTestConfig.class)
class ActivityTrackerApplicationTests {

	@Test
	void contextLoads() {
	}

}
