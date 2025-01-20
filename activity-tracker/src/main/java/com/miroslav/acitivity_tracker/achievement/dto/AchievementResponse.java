package com.miroslav.acitivity_tracker.achievement.dto;

import com.miroslav.acitivity_tracker.achievement.model.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public record AchievementResponse(
        String name,
        String info,
        Type type
) {
}
