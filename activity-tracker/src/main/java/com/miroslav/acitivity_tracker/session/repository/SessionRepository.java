package com.miroslav.acitivity_tracker.session.repository;

import com.miroslav.acitivity_tracker.session.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    @Query("""
            SELECT session
            FROM Session session
            WHERE session.activity.profile.profileId = :profileId
            AND session.activity.activityId = :activityId
            AND session.sessionId = :sessionId
            """)
    Optional<Session> findFromProfile(Integer sessionId, Integer activityId, Integer profileId);

    @Query("""
            SELECT session
            FROM Session session
            WHERE session.activity.profile.profileId = :profileId
            AND session.activity.activityId = :activityId
            """)
    List<Session> findAllSessions(Integer activityId, Integer profileId);
    @Query("""
            SELECT session
            FROM Session session
            WHERE session.activity.profile.profileId = :profileId
            AND session.activity.activityId = :activityId
            """)
    Page<Session> findAllSessions(Integer activityId, Integer profileId, Pageable pageable);
}
