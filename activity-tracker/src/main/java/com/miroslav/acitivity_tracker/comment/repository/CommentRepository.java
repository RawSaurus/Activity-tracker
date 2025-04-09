package com.miroslav.acitivity_tracker.comment.repository;

import com.miroslav.acitivity_tracker.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

    Page<Comment> findAllByTemplateTemplateId(Integer templateId, Pageable pageable);
}
