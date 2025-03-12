package com.miroslav.acitivity_tracker.achievement.dto;

import com.miroslav.acitivity_tracker.achievement.model.Type;

import java.util.Date;

public record AchievementResponseWType(
        String name,
        String info,
        Type type,
        int xp,
        String unit,
        Date deadline,
        int currentStreak,
        int biggestStreak
) {
}
