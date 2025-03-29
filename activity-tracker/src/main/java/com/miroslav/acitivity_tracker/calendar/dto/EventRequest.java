package com.miroslav.acitivity_tracker.calendar.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record EventRequest(
        String name,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime start,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime end
) {
}
