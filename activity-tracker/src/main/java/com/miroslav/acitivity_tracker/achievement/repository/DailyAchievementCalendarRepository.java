package com.miroslav.acitivity_tracker.achievement.repository;

import com.miroslav.acitivity_tracker.achievement.model.DailyAchievementCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DailyAchievementCalendarRepository extends JpaRepository<DailyAchievementCalendar, Integer> {


    Optional<DailyAchievementCalendar> findByDailyAchievement_TypeAchievementId(Integer typeAchievementId);

//    void deleteByDailyAchievementTypeAchievementId(Integer achievementId);
}
