package com.miroslav.acitivity_tracker.comment.repository;

import com.miroslav.acitivity_tracker.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
