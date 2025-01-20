package com.miroslav.acitivity_tracker.template.dto;

import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.util.enums.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record TemplateRequest(

        @NotEmpty(message = "Template needs a name")
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotEmpty(message = "Write some info about template")
        String info,
        @NotEmpty(message = "Choose a type for template")
        String type,
        @EnumValidator(enumClass = Category.class)
        String category
) {
}
