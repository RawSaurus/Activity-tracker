package com.miroslav.acitivity_tracker.session.service;

import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.calendar.model.Event;
import com.miroslav.acitivity_tracker.calendar.model.EventType;
import com.miroslav.acitivity_tracker.calendar.repository.EventRepository;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.session.dto.SessionRequest;
import com.miroslav.acitivity_tracker.session.dto.SessionResponse;
import com.miroslav.acitivity_tracker.session.mapper.SessionMapper;
import com.miroslav.acitivity_tracker.session.model.Session;
import com.miroslav.acitivity_tracker.session.repository.SessionRepository;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserContext userContext;
    private final SessionRepository sessionRepository;
    private final ActivityRepository activityRepository;
    private final ProfileRepository profileRepository;
    private final EventRepository eventRepository;
    private final SessionMapper sessionMapper;

    public SessionResponse findById(Integer sessionId){
        return sessionMapper.toResponse(sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found")));
    }
    public SessionResponse findSessionFromProfile(Integer sessionId, Integer activityId) {
        return sessionRepository.findFromProfile(
                sessionId,
                activityId,
                (userContext.getAuthenticatedUser().getUserId())
        )
                .map(sessionMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));
    }

    public Page<SessionResponse> findAllSessions(Integer activityId, Pageable pageable) {
        return sessionRepository.findAllSessions(
                activityId,
                (userContext.getAuthenticatedUser().getUserId()),
                pageable
        ).map(sessionMapper::toResponse);
    }

    //TODO if name is empty put time as name
    public Integer createSession(Integer activityId, SessionRequest request) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        Session session = sessionMapper.toEntity(request);
        session.setStart(LocalDateTime.now());
        activity.getSessions().add(session);
//        Event event = Event.builder()
//                .name(session.getName())
//                .day(LocalDateTime.now())
//                .build();
//
//        eventRepository.save(event);

        return sessionRepository.save(session).getSessionId();
    }

    public Integer createSessionWithTime(Integer activityId, SessionRequest request) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        Session session = sessionMapper.toEntity(request);
        session.setDuration(request.duration());
        activity.getSessions().add(session);

        Integer sessionId = sessionRepository.save(session).getSessionId();

        //TODO start and end values in event won't be correct. Find solution

        Event event = Event.builder()
                .name(session.getName())
                .type(EventType.SESSION)
                .linkId(sessionId)
                .start(session.getStart())
                .end(LocalDateTime.now())
                .profile(profile)
                .build();

        eventRepository.save(event);

        return sessionId;
    }

    public Integer endSession(Integer activityId, Integer sessionId) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Session session = sessionRepository.findFromProfile(sessionId, activityId, profile.getProfileId())
                        .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        session.setFinish(LocalDateTime.now());

        Event event = Event.builder()
                .name(session.getName())
                .type(EventType.SESSION)
                .linkId(sessionId)
                .start(session.getStart())
                .end(LocalDateTime.now())
                .profile(profile)
                .build();

        eventRepository.save(event);

        return sessionRepository.save(session).getSessionId();
    }

    public Integer updateSession(Integer activityId, Integer sessionId, SessionRequest request) {
        Session session = sessionRepository.findFromProfile(sessionId, activityId, (userContext.getAuthenticatedUser().getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        sessionMapper.updateToEntity(request, session);

        return sessionRepository.save(session).getSessionId();
    }

    public Integer addNote(Integer activityId, Integer sessionId, String note) {
        Session session = sessionRepository.findFromProfile(sessionId, activityId, (userContext.getAuthenticatedUser().getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        session.getNotes().add(note);

        return sessionRepository.save(session).getSessionId();
    }

    public String deleteSession(Integer activityId, Integer sessionId) {
        Session session = sessionRepository.findFromProfile(sessionId, activityId, (userContext.getAuthenticatedUser().getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        sessionRepository.delete(session);

        return "Session deleted successfully";
    }
}

