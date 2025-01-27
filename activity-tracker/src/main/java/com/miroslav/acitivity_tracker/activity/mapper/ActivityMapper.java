package com.miroslav.acitivity_tracker.activity.mapper;

import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import org.mapstruct.*;

import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    @Mapping(target = "isPrivate", ignore = true)
    ActivityRequest toRequest(Activity activity);
    ActivityResponse toResponse(Activity activity);
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "sessions", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "activityId", ignore = true)
    @Mapping(target = "achievements", ignore = true)
    Activity toEntity(ActivityRequest request);
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "sessions", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "isPrivate", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "activityId", ignore = true)
    @Mapping(target = "achievements", ignore = true)
    Activity toEntity(ActivityResponse response);

    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "sessions", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "activityId", ignore = true)
    @Mapping(target = "achievements", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateToEntity(ActivityRequest request, @MappingTarget Activity activity);

    void updateToEntity(Activity original, @MappingTarget Activity copy);
}
