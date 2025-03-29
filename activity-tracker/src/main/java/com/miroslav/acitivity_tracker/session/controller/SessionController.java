package com.miroslav.acitivity_tracker.session.controller;

import com.miroslav.acitivity_tracker.session.dto.SessionRequest;
import com.miroslav.acitivity_tracker.session.dto.SessionResponse;
import com.miroslav.acitivity_tracker.session.model.Session;
import com.miroslav.acitivity_tracker.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("session")
public class SessionController {

    private final SessionService sessionService;

    //TODO create tests
    //TODO add authorization

    //TODO make into response
    //TODO always returns 1
    @GetMapping("/{session-id}")
    public ResponseEntity<SessionResponse> findById(@PathVariable("session-id")Integer sessionId){
        return ResponseEntity.ok(sessionService.findById(sessionId));
    }

    @GetMapping("/{activity-id}/{session-id}")
    public ResponseEntity<SessionResponse> findSession(@PathVariable("session-id") Integer sessionId,
                                                       @PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(sessionService.findSessionFromProfile(sessionId, activityId));
    }

    @GetMapping("/all/{activity-id}")
    public ResponseEntity<List<SessionResponse>> findAllSessions(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(sessionService.findAllSessions(activityId));
    }

    @PostMapping("/{activity-id}")
    public ResponseEntity<Integer> createSession(@PathVariable("activity-id") Integer activityId,
                                                 @RequestBody SessionRequest request){
        return ResponseEntity.accepted().body(sessionService.createSession(activityId, request));
    }

    @PostMapping("/input-time/{activity-id}")
    public ResponseEntity<Integer> createSessionWithTime(@PathVariable("activity-id") Integer activityId,
                                                 @RequestBody SessionRequest request){
        return ResponseEntity.accepted().body(sessionService.createSessionWithTime(activityId, request));
    }

    @PutMapping("/{activity-id}/end-session/{session-id}")
    public ResponseEntity<Integer> endSession(@PathVariable("activity-id") Integer activityId,
                                              @PathVariable("session-id") Integer sessionId){
        return ResponseEntity.ok(sessionService.endSession(activityId, sessionId));
    }

    @PutMapping("/{activity-id}/{session-id}")
    public ResponseEntity<Integer> updateSession(@PathVariable("activity-id") Integer activityId,
                                                 @PathVariable("session-id") Integer sessionId,
                                                 @RequestBody SessionRequest request){
        return ResponseEntity.ok(sessionService.updateSession(activityId, sessionId, request));
    }

    @PutMapping("/add-note/{activity-id}/{session-id}")
    public ResponseEntity<Integer> addNote(@PathVariable("activity-id") Integer activityId,
                                           @PathVariable("session-id") Integer sessionId,
                                           @RequestBody String note){
        return ResponseEntity.ok(sessionService.addNote(activityId, sessionId, note));
    }

    @DeleteMapping("/{activity-id}/{session-id}")
    public ResponseEntity deleteSession(@PathVariable("activity-id") Integer activityId,
                                        @PathVariable("session-id") Integer sessionId){
        return sessionService.deleteSession(activityId, sessionId);
    }

}
