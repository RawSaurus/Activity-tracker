package com.miroslav.acitivity_tracker.calendar.repository;

import com.miroslav.acitivity_tracker.calendar.module.Event;
import com.miroslav.acitivity_tracker.calendar.module.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Optional<Event> findByEventIdAndProfileProfileId(Integer eventId, Integer profileId);

    List<Event> findAllByProfileProfileId(Integer profileId);

    @Query("""
            SELECT e
            FROM Event e
            WHERE e.profile.profileId = :profileId
            AND e.start >= :start
            AND e.end <= :end
            """)
    List<Event> findAllByStartBetween(Integer profileId, LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT e
            FROM Event e
            WHERE e.profile.profileId = :profileId
            AND e.type = :type
            """)
    List<Event> findAllByProfileProfileIdAndType(Integer profileId, EventType type);

    @Query("""
            SELECT e
            FROM Event e
            WHERE e.profile.profileId = :profileId
            AND e.type = :type
            AND e.start >= :start
            AND e.end <= :end
            """)
    List<Event> findAllByStartBetweenAndType(Integer profileId, LocalDateTime start, LocalDateTime end, EventType type);
}
