package com.miroslav.acitivity_tracker.calendar.dto;

import com.miroslav.acitivity_tracker.calendar.module.EventType;

import java.time.LocalDateTime;

public record EventResponse(
        String name,
        LocalDateTime start,
        LocalDateTime end,
        EventType type,
        Integer linkId
) {
}
