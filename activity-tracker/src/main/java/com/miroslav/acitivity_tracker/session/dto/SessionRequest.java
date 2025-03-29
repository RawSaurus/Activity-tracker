package com.miroslav.acitivity_tracker.session.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.sql.Timestamp;

public record SessionRequest(
        String name,
        String info,
        Timestamp duration
) {
}
