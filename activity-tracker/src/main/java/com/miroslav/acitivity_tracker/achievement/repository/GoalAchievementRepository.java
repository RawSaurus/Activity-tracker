package com.miroslav.acitivity_tracker.achievement.repository;

import com.miroslav.acitivity_tracker.achievement.model.GoalAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalAchievementRepository extends TypeSuperclassRepository<GoalAchievement, Integer> {
}
