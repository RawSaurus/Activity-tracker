package com.miroslav.acitivity_tracker.util.enums;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.calendar.module.EventType;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, EventType> {

    @Override
    public EventType convert(String source) {
        return EventType.valueOf(source.toUpperCase());
    }
}
