package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.sun.source.tree.ModuleTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextCustomizerFactories;
import org.springframework.test.context.TestPropertySource;


@DataJpaTest(properties = {"command.line.runner.enabled=false", "application.runner.enabled=false"})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = {"classpath:application-test.yml"})
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(value = "classpath:application-test.yml")
public class ActivityInMemoryTest {


    @Autowired
    private ActivityRepository activityRepository;

    @Test
    public void should_save(){

        Activity activity = Activity.builder()
                .name("activity")
                .info("info")
                .build();

        Activity savedActivity = activityRepository.save(activity);

        Assertions.assertNotNull(savedActivity);
        Assertions.assertEquals("activity", savedActivity.getName());
    }
}
