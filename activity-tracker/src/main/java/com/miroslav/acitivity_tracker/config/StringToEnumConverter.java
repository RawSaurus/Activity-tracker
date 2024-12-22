package com.miroslav.acitivity_tracker.config;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, Category> {
    @Override
    public Category convert(String source) {
        return Category.valueOf(source.toUpperCase());
    }
}
