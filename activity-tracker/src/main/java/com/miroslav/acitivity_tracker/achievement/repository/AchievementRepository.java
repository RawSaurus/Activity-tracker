package com.miroslav.acitivity_tracker.achievement.repository;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {


//    Optional<Achievement> findByName();

    @Query("""
            SELECT achievement
            FROM Achievement achievement
            WHERE achievement.achievementId = :achievementId
            AND achievement.activity.activityId = :activityId
            AND achievement.activity.isPrivate = false
            """)
    Optional<Achievement> findPublicById(Integer achievementId, Integer activityId);
    @Query("""
            SELECT achievement
            FROM Achievement achievement
            WHERE achievement.activity.activityId = :activityId
            AND achievement.activity.isPrivate = false
            """)
    List<Achievement> findAllPublicById(Integer activityId);

    Page<Achievement> findAllByActivityActivityId(Integer activityId, Pageable pageable);

    @Query("""
            SELECT achievement
            FROM Achievement achievement
            WHERE achievement.activity.profile.profileId = :profileId
            AND achievement.activity.activityId = :activityId
            AND achievement.achievementId = :achievementId
            """)
    Optional<Achievement> findFromProfile(Integer achievementId, Integer activityId, Integer profileId);

    @Transactional
    @Modifying
    @Query("""
    UPDATE Achievement a
    SET a.finished = true
    WHERE a.achievementId = :achievementId
    """)
    void updateFinished(Integer achievementId);
}
