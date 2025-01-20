package com.miroslav.acitivity_tracker.template.mapper;

import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.template.dto.TemplateRequest;
import com.miroslav.acitivity_tracker.template.dto.TemplateResponse;
import com.miroslav.acitivity_tracker.template.model.Template;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    @Mapping(target = "templateId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "downloads", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "achievements", ignore = true)
    Template toEntity(TemplateRequest request);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "templateId", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "downloads", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "achievements", ignore = true)
    Template toEntity(ActivityRequest request);

    @Mapping(target = "templateId", ignore = true)
    @Mapping(target = "achievements", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Template toEntity(Activity activity);

    TemplateResponse toResponse(Template template);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "templateId", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "downloads", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "achievements", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateToEntity(@MappingTarget Template template, TemplateRequest request);
}
