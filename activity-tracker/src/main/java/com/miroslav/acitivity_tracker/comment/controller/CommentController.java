package com.miroslav.acitivity_tracker.comment.controller;

import com.miroslav.acitivity_tracker.comment.dto.CommentRequest;
import com.miroslav.acitivity_tracker.comment.dto.CommentResponse;
import com.miroslav.acitivity_tracker.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{comment-id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable("comment-id") Integer commentId){
        return ResponseEntity.ok(commentService.findById(commentId));
    }

    @PostMapping("/{template-id}")
    public ResponseEntity<Integer> createComment(@RequestBody @Valid CommentRequest request, @PathVariable("template-id") Integer templateId){
        return ResponseEntity.ok(commentService.createComment(request, templateId));
    }

    @PutMapping("/{comment-id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("comment-id") Integer commentId, @RequestBody @Valid CommentRequest request){
        return ResponseEntity.ok(commentService.updateComment(commentId, request));
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity<String> deleteComment(@PathVariable("comment-id") Integer commentId){
        return ResponseEntity.ok(commentService.deleteComment(commentId));
    }


}
