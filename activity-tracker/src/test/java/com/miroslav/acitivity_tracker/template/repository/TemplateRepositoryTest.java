package com.miroslav.acitivity_tracker.template.repository;

import com.miroslav.acitivity_tracker.ActivityTrackerApplication;
import com.miroslav.acitivity_tracker.RepositoryTestConfig;
import com.miroslav.acitivity_tracker.template.TemplateTestConfig;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class TemplateRepositoryTest {

    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Template template1;
    private Template template2;
    private Profile savedProfile;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setFirstName("first");
        user.setLastName("last");
        User savedUser = testEntityManager.persist(user);
        Profile profile = Profile.builder()
                .profileId(savedUser.getUserId())
                .user(savedUser)
                .username("username")
                .build();
        savedProfile = testEntityManager.persist(profile);


        template1 = Template.builder()
                .name("Template 1")
                .info("Description 1")
                .profile(savedProfile)
                .build();

        template2 = Template.builder()
                .name("Template 2")
                .info("Description 2")
                .profile(savedProfile)
                .build();

        templateRepository.save(template1);
        templateRepository.save(template2);
    }

    @AfterEach
    public void tearDown() {
        templateRepository.deleteAll();
    }


    @Test
    public void should_find_template_by_name(){
        Optional<Template> foundTemplate = templateRepository.findByName("Template 1");
        assertTrue(foundTemplate.isPresent());
        assertEquals(template1.getName(), foundTemplate.get().getName());
    }

    @Test
    public void should_find_all_by_profile_id(){
        Pageable pageable = PageRequest.of(0, 1, Sort.Direction.ASC, "name");
        Pageable pageable2 = PageRequest.of(1, 1, Sort.Direction.ASC, "name");

        Page<Template> foundPage1 = templateRepository.findAllByProfileProfileId(savedProfile.getProfileId(), pageable);
        Page<Template> foundPage2 = templateRepository.findAllByProfileProfileId(savedProfile.getProfileId(), pageable2);

        assertEquals(1 ,foundPage1.getContent().size());
        assertEquals(1 ,foundPage2.getContent().size());
        assertEquals(template1, foundPage1.getContent().get(0));
        assertEquals(template2, foundPage2.getContent().get(0));
    }

    @Test
    public void should_find_template_by_id() {
        Optional<Template> foundTemplate = templateRepository.findById(template1.getTemplateId());
        assertTrue(foundTemplate.isPresent());
        assertEquals(template1.getName(), foundTemplate.get().getName());
    }

    @Test
    public void should_find_all_templates() {
        List<Template> templates = templateRepository.findAll();
        assertNotNull(templates);
        assertEquals(2, templates.size());
    }

    @Test
    public void should_save_template() {
        Template newTemplate = Template.builder()
                .name("Template 3")
                .info("Description 3")
                .build();

        Template savedTemplate = templateRepository.save(newTemplate);
        assertNotNull(savedTemplate);
        assertEquals("Template 3", savedTemplate.getName());
    }

    @Test
    public void should_delete_template_by_id() {
        templateRepository.deleteById(template1.getTemplateId());
        Optional<Template> deletedTemplate = templateRepository.findById(template1.getTemplateId());
        assertTrue(deletedTemplate.isEmpty());
    }

    @Test
    public void should_update_template() {
        template1.setName("Updated Template 1");
        Template updatedTemplate = templateRepository.save(template1);

        assertNotNull(updatedTemplate);
        assertEquals("Updated Template 1", updatedTemplate.getName());
    }
}