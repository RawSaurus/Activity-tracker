package com.miroslav.acitivity_tracker.comment.dto;

public record CommentResponse(
        String header,
        String text,
        int likes
) {
}
