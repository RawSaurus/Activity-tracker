package com.miroslav.acitivity_tracker.template.dto;

import com.miroslav.acitivity_tracker.activity.model.Category;

public record TemplateResponse(

        String name,
        String info,
        String type,
        Category category
) {
}
