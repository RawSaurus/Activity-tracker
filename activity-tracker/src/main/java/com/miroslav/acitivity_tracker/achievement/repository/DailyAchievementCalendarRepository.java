package com.miroslav.acitivity_tracker.achievement.repository;

import com.miroslav.acitivity_tracker.achievement.model.DailyAchievementCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyAchievementCalendarRepository extends JpaRepository<DailyAchievementCalendar, Integer> {
}
