package com.miroslav.acitivity_tracker.activity.dto;

import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.util.enums.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ActivityRequest(
        @NotEmpty(message = "Activity needs a name")
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotEmpty(message = "Write some info about activity")
        String info,
        @NotEmpty(message = "Choose a type for activity")
        String type,
        @EnumValidator(enumClazz = Category.class)
        String category,
        boolean isPrivate
) {
}
