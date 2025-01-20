package com.miroslav.acitivity_tracker.session.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record SessionRequest(
        String name,
        String info
) {
}
