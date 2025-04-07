package com.miroslav.acitivity_tracker.file.dto;

public record FileResponse(
        String name,
        String fileCode,
        Long size
) {
}
