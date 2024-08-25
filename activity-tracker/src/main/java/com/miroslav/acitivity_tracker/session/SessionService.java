package com.miroslav.acitivity_tracker.session;

import com.miroslav.acitivity_tracker.activity.ActivityRepository;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ActivityRepository activityRepository;
    private final ProfileRepository profileRepository;
    private final SessionMapper sessionMapper;


//    TODO test
    public SessionResponse findSession(Integer activityId, Integer sessionId, Authentication user) {
        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

        return sessionMapper.toResponse(profile.getActivities()
                .stream()
                .filter(activity -> activity.getActivityId().equals(activityId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"))
                .getSessions()
                .stream()
                .filter(session -> session.getSessionId().equals(sessionId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Session not found")));
    }

//    TODO test
    public List<SessionResponse> findAllSessions(Integer activityId, Authentication user) {
        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

        return profile.getActivities()
                .stream()
                .filter(activity -> activity.getActivityId().equals(activityId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"))
                .getSessions()
                .stream()
                .map(sessionMapper::toResponse)
                .collect(Collectors.toList());
    }

//    TODO test
    public Integer createSession(Integer activityId, SessionRequest request, Authentication user) {
        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = profile.getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        Session session = sessionMapper.toEntity(request);
        session.setStart(LocalDateTime.now());
        activity.getSessions().add(session);

        profileRepository.save(profile);
        activityRepository.save(activity);
        return sessionRepository.save(session).getSessionId();
    }

    //TODO test
    public Integer createSessionWithTime(Integer activityId, SessionRequest request, Timestamp duration, Authentication user) {
        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = profile.getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        Session session = sessionMapper.toEntity(request);
        session.setDuration(duration);
        activity.getSessions().add(session);

        profileRepository.save(profile);
        activityRepository.save(activity);
        return sessionRepository.save(session).getSessionId();
    }

    //TODO test
    public Integer endSession(Integer activityId, Integer sessionId, Authentication user) {
        Session session = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"))
                .getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"))
                .getSessions()
                .stream()
                .filter(s -> s.getSessionId().equals(sessionId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        session.setFinish(LocalDateTime.now());
        return sessionRepository.save(session).getSessionId();

    }

    //TODO test
    public Integer updateSession(Integer activityId, Integer sessionId, SessionRequest request, Authentication user) {
        Session session = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"))
                .getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"))
                .getSessions()
                .stream()
                .filter(s -> s.getSessionId().equals(sessionId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        sessionMapper.updateToEntity(request, session);

        return sessionRepository.save(session).getSessionId();

    }

//    TODO test
    public Integer addNote(Integer activityId, Integer sessionId, String note, Authentication user) {
        Session session = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"))
                .getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"))
                .getSessions()
                .stream()
                .filter(s -> s.getSessionId().equals(sessionId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        session.getNotes().add(note);

        return sessionRepository.save(session).getSessionId();
    }

//    TODO test
    public ResponseEntity deleteSession(Integer activityId, Integer sessionId, Authentication user) {
        Session session = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"))
                .getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"))
                .getSessions()
                .stream()
                .filter(s -> s.getSessionId().equals(sessionId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        sessionRepository.delete(session);

        return ResponseEntity.ok("Session deleted successfully");
    }
}

