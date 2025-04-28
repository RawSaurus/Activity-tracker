package com.miroslav.acitivity_tracker.comment.controller;

import com.miroslav.acitivity_tracker.comment.dto.CommentRequest;
import com.miroslav.acitivity_tracker.comment.dto.CommentResponse;
import com.miroslav.acitivity_tracker.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{comment-id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable("comment-id") Integer commentId){
        return ResponseEntity.ok(commentService.findById(commentId));
    }

    @GetMapping("/template/{template-id}")
    public ResponseEntity<Page<CommentResponse>> findByTemplateId(
            @PathVariable("template-id") Integer templateId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "postedAt") String sortBy,
            @RequestParam(value = "sort-direction", defaultValue = "asc") String sortDirection
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(commentService.findAllByTemplateId(templateId, pageable));
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
