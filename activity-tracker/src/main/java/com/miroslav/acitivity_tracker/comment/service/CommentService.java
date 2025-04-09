package com.miroslav.acitivity_tracker.comment.service;

import com.miroslav.acitivity_tracker.comment.dto.CommentRequest;
import com.miroslav.acitivity_tracker.comment.dto.CommentResponse;
import com.miroslav.acitivity_tracker.comment.mapper.CommentMapper;
import com.miroslav.acitivity_tracker.comment.model.Comment;
import com.miroslav.acitivity_tracker.comment.repository.CommentRepository;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.template.repository.TemplateRepository;
import com.miroslav.acitivity_tracker.user.model.Profile;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserContext userContext;
    private final CommentRepository commentRepository;
    private final TemplateRepository templateRepository;
    private final CommentMapper commentMapper;


    public CommentResponse findById(Integer commentId) {
        return commentMapper.toResponse(
                commentRepository.findById(commentId)
                        .orElseThrow(() -> new EntityNotFoundException("Comment not found"))
        );
    }

    public Page<CommentResponse> findAllByTemplateId(Integer templateId, Pageable pageable) {
        return commentRepository.findAllByTemplateTemplateId(templateId, pageable)
                .map(commentMapper::toResponse);
    }

    public Integer createComment(CommentRequest request, Integer templateId) {
        Comment comment = commentMapper.toEntity(request);
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("Template not found"));
        comment.setTemplate(template);
        return commentRepository.save(comment).getCommentId();
    }

    public CommentResponse updateComment(Integer commentId, CommentRequest request) {
         Comment comment = commentRepository.findById(commentId)
                 .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

         commentMapper.updateToEntity(comment, request);

         return commentMapper.toResponse(commentRepository.save(comment));
    }

    public String deleteComment(Integer commentId) {
//        Profile profile = userContext.getAuthenticatedUser().getProfile();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if(!comment.getCreatedBy().equals(userContext.getAuthenticatedUser().getUserId())){
            throw new ActionNotAllowed("You are not allowed to delete this comment");
        }
        commentRepository.delete(comment);
        return "Comment deleted successfully";
    }
}
