package com.miroslav.acitivity_tracker.achievement;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {


//    Optional<Achievement> findByName();

//    @Query("""
//            SELECT achievement
//            FROM Achievement achievement
//            WHERE achievement.achievement_id = id
//            AND achievement.isPrivate = false
//            """)
//    Optional<Achievement> findPublicById(Integer id);

}
