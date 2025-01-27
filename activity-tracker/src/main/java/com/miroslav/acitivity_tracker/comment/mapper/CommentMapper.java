package com.miroslav.acitivity_tracker.comment.mapper;

import com.miroslav.acitivity_tracker.comment.dto.CommentRequest;
import com.miroslav.acitivity_tracker.comment.dto.CommentResponse;
import com.miroslav.acitivity_tracker.comment.model.Comment;
import org.mapstruct.*;

import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "template", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "commentId", ignore = true)
    Comment toEntity(CommentRequest request);

    CommentResponse toResponse(Comment comment);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "template", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "commentId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateToEntity(@MappingTarget Comment comment, CommentRequest request);
}
