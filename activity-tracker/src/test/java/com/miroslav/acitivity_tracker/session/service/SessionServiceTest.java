package com.miroslav.acitivity_tracker.session.service;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.calendar.model.Event;
import com.miroslav.acitivity_tracker.calendar.repository.EventRepository;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.session.dto.SessionRequest;
import com.miroslav.acitivity_tracker.session.dto.SessionResponse;
import com.miroslav.acitivity_tracker.session.mapper.SessionMapper;
import com.miroslav.acitivity_tracker.session.mapper.SessionMapperImpl;
import com.miroslav.acitivity_tracker.session.model.Session;
import com.miroslav.acitivity_tracker.session.repository.SessionRepository;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private UserContext userContext;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Spy
    private SessionMapper sessionMapper = new SessionMapperImpl();
    @InjectMocks
    private SessionService sessionService;

    SessionServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    private User user;
    private Profile profile;
    private Activity activity;
    private Session session1;
    private Session session2;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .userId(1)
                .email("mail.test")
                .build();
        profile = Profile.builder()
                .profileId(1)
                .user(user)
                .username("username")
                .build();
        user.setProfile(profile);
        activity = Activity.builder()
                .activityId(1)
                .profile(profile)
                .name("activity")
                .sessions(new ArrayList<>())
                .build();
        session1 = Session.builder()
                .sessionId(1)
                .activity(activity)
                .name("session1")
                .build();
        session2 = Session.builder()
                .sessionId(2)
                .activity(activity)
                .name("session2")
                .build();
    }

    @Test
    @DisplayName("findById should return session response when it exists")
    public void findSessionById_shouldReturnSessionResponse_whenItExists() {
        when(sessionRepository.findById(session1.getSessionId())).thenReturn(Optional.of(session1));

        SessionResponse response = sessionService.findById(session1.getSessionId());

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("session1");
    }

    @Test
    @DisplayName("findSessionById should throw exception when session not found")
    public void findSessionById_shouldThrowException_whenSessionNotFound() {
        when(sessionRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sessionService.findById(999));
    }

    @Test
    @DisplayName("findSessionFromProfile should return session response")
    public void findSessionFromProfile_shouldReturnSessionResponse_whenGivenSessionAndActivityId(){
        when(sessionRepository.findFromProfile(session1.getSessionId(), activity.getActivityId(), user.getUserId())).thenReturn(Optional.of(session1));
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        SessionResponse response = sessionService.findSessionFromProfile(session1.getSessionId(), activity.getActivityId());

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo(session1.getName());
    }

    @Test
    @DisplayName("findAllSessions should return paginated sessions")
    public void findAllSessions_shouldReturnPaginatedSessions() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Session> page = new PageImpl<>(List.of(session1, session2), pageable, 2);

        when(sessionRepository.findAllSessions(activity.getActivityId(), user.getUserId(), pageable)).thenReturn(page);
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        Page<SessionResponse> response = sessionService.findAllSessions(activity.getActivityId(), pageable);

        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0).name()).isEqualTo("session1");
    }

    @Test
    @DisplayName("createSession should save and return session")
    public void createSession_shouldSaveAndReturnSession() {
        SessionRequest request = new SessionRequest("request","info", Timestamp.valueOf(LocalDateTime.now()));

        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(profile.getProfileId())).thenReturn(Optional.of(profile));
        when(activityRepository.findById(activity.getActivityId())).thenReturn(Optional.of(activity));
        when(sessionRepository.save(any())).thenReturn(session1);

        Integer response = sessionService.createSession(activity.getActivityId(), request);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(session1.getSessionId());
    }

    @Test
    @DisplayName("createSessionWithTime should save session with given time values")
    public void createSessionWithTime_shouldSaveSession_givenTimeValues(){
        SessionRequest request = new SessionRequest("request","info", Timestamp.valueOf(LocalDateTime.now()));
        Session sessionToSave = Session.builder()
                .sessionId(3)
                .duration(request.duration())
                .name("new session")
                .build();
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(profile.getProfileId())).thenReturn(Optional.of(profile));
        when(activityRepository.findById(activity.getActivityId())).thenReturn(Optional.of(activity));
        when(sessionRepository.save(any())).thenReturn(sessionToSave);
        when(eventRepository.save(any())).thenReturn(mock(Event.class));

        Integer response = sessionService.createSessionWithTime(activity.getActivityId(), request);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(sessionToSave.getSessionId());
    }

    @Test
    @DisplayName("deleteSession should remove session when it exists")
    public void deleteSession_shouldRemoveSession_whenItExists() {
        when(sessionRepository.findFromProfile(session1.getSessionId(), activity.getActivityId(), user.getUserId())).thenReturn(Optional.of(session1));
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        doNothing().when(sessionRepository).delete(session1);

        ResponseEntity response = sessionService.deleteSession(activity.getActivityId(), session1.getSessionId());

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo("Session deleted successfully");
    }

    @Test
    @DisplayName("deleteSession should throw exception when session not found")
    public void deleteSession_shouldThrowException_whenSessionNotFound() {
        when(sessionRepository.findFromProfile(999,999, user.getUserId())).thenReturn(Optional.empty());
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        assertThrows(EntityNotFoundException.class, () -> sessionService.deleteSession(999, 999));
    }
}
