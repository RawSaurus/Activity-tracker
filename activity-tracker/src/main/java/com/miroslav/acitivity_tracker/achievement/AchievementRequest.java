package com.miroslav.acitivity_tracker.achievement;

import com.miroslav.acitivity_tracker.achievement.model.Type;

public record AchievementRequest(
        String name,
        String info,
        Type type
) {
}
