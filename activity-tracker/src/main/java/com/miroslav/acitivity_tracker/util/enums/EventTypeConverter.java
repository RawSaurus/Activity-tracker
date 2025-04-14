package com.miroslav.acitivity_tracker.util.enums;

import com.miroslav.acitivity_tracker.calendar.model.EventType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EventTypeConverter implements Converter<String, EventType> {

    @Override
    public EventType convert(String source) {
        return EventType.valueOf(source.toUpperCase());
    }
}
