package com.miroslav.acitivity_tracker.achievement.dto;

import com.miroslav.acitivity_tracker.achievement.model.Type;
import com.miroslav.acitivity_tracker.util.enums.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record AchievementRequest(
        @NotEmpty(message = "Achievement needs a name")
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotEmpty(message = "Write some info about achievement")
        String info,
        @EnumValidator(enumClass = Type.class)
        String type

) {
}
