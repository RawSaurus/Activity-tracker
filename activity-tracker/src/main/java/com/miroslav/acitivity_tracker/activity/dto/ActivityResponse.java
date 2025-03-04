package com.miroslav.acitivity_tracker.activity.dto;

import com.miroslav.acitivity_tracker.activity.model.Category;
import lombok.Builder;

public record ActivityResponse(
        String name,
//        String group,
        String info,
        String type,
        Category category
) {
}
