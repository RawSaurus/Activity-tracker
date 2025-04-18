package com.miroslav.acitivity_tracker.session.repository;

import com.miroslav.acitivity_tracker.RepositoryTestConfig;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.session.model.Session;
import com.miroslav.acitivity_tracker.user.model.Profile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Profile profile;
    private Activity activity;
    private Session session1;
    private Session session2;

    @BeforeEach
    void setUp() {
        Profile profileToSave = Profile.builder()
                .profileId(1)
                .build();

        profile = testEntityManager.persist(profileToSave);

        Activity activityToSave = Activity.builder()
                .profile(profile)
                .build();

        activity = testEntityManager.persist(activityToSave);

        session1 = Session.builder()
                .activity(activity)
                .name("session1")
                .build();
        session2 = Session.builder()
                .activity(activity)
                .name("session2")
                .build();

        sessionRepository.saveAll(List.of(session1, session2));
    }

    @AfterEach
    public void tearDown(){
        sessionRepository.deleteAll();
    }

    @Test
    @DisplayName("findFromProfile should return session when it exists")
    void findFromProfile_shouldReturnSession_whenItExists() {
        Optional<Session> foundSession = sessionRepository.findFromProfile(session1.getSessionId(), activity.getActivityId(), profile.getProfileId());

        assertThat(foundSession).isPresent();
        assertThat(foundSession.get().getName()).isEqualTo(session1.getName());
    }

    @Test
    @DisplayName("findFromProfile should return empty when session does not exist")
    void findFromProfile_shouldReturnEmpty_whenSessionDoesNotExist() {
        Optional<Session> foundSession = sessionRepository.findFromProfile(999, 1, 1);

        assertThat(foundSession).isEmpty();
    }

    @Test
    @DisplayName("findAllSessions should return all sessions for a given activity and profile")
    void findAllSessions_shouldReturnAllSessions_forGivenActivityAndProfile() {
        List<Session> sessions = sessionRepository.findAllSessions(activity.getActivityId(), profile.getProfileId());

        assertThat(sessions).hasSize(2);
        assertThat(sessions.get(0).getName()).isEqualTo(session1.getName());
    }

    @Test
    @DisplayName("findAllSessions should return empty list when no sessions exist")
    void findAllSessions_shouldReturnEmptyList_whenNoSessionsExist() {
        List<Session> sessions = sessionRepository.findAllSessions(999, 1);

        assertThat(sessions).isEmpty();
    }

    @Test
    @DisplayName("findAllSessions should return page of session for given activity and profile")
    public void findAllSessions_shouldReturnPageOfSessions_forGivenActivityAndProfile(){
        Pageable pageable = PageRequest.of(0, 2);

        Page<Session> page = sessionRepository.findAllSessions(activity.getActivityId(), profile.getProfileId(), pageable);

        assertThat(page.getContent()).isNotNull();
        assertThat(page.getContent().size()).isEqualTo(2);
        assertThat(page.getContent().get(0).getName()).isEqualTo(session1.getName());
    }
}
