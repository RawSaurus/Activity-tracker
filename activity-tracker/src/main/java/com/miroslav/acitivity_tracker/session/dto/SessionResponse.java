package com.miroslav.acitivity_tracker.session.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record SessionResponse(
        String name,
        String info,
        LocalDateTime start,
        LocalDateTime finish,
        Timestamp duration

) {
}
