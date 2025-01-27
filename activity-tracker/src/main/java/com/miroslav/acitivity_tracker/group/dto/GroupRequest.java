package com.miroslav.acitivity_tracker.group.dto;

import jakarta.validation.constraints.Size;

public record GroupRequest(
        @Size(max=20,message = "name can be max 20 characters long")
        String name
) {
}
