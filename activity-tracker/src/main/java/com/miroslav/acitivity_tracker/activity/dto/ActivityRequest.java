package com.miroslav.acitivity_tracker.activity.dto;

import com.miroslav.acitivity_tracker.activity.model.Category;

public record ActivityRequest(
        String name,
        String info,
        String type,
        Category category,
        boolean isPrivate
) {
}
