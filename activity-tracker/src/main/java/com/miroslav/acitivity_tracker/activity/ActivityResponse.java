package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.model.Category;
import lombok.Builder;

public record ActivityResponse(
        String name,
//        String group,
        String info,
        String type,
        Category category,
        double rating,
        int downloads
) {
}
