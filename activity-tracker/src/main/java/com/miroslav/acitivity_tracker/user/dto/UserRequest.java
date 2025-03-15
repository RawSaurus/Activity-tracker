package com.miroslav.acitivity_tracker.user.dto;

public record UserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String role
) {
}
