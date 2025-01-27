package com.miroslav.acitivity_tracker.comment.dto;

import jakarta.validation.constraints.*;

public record CommentRequest(
        @NotNull(message = "Comment must have header")
        @NotBlank(message = "Header cannot be blank")
        @Size(max = 60, message = "Header can have max 60 characters")
        String header,
        @NotNull(message = "Comment must have some text")
        @NotBlank(message = "Comment cannot be blank")
        @Size(max = 250, message = "Text can have max 250 characters")
        String text
) {
}
