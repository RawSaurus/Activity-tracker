package com.miroslav.acitivity_tracker.achievement;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.activity.ActivityRequest;
import org.mapstruct.*;

import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(componentModel = "spring")
public interface AchievementMapper {

    AchievementRequest toRequest(Achievement achievement);

    AchievementResponse toResponse(Achievement achievement);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "typeCheckmark", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "activity", ignore = true)
    @Mapping(target = "achievementId", ignore = true)
    Achievement toEntity(AchievementRequest request);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "typeCheckmark", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "activity", ignore = true)
    @Mapping(target = "achievementId", ignore = true)
    Achievement toEntity(AchievementResponse response);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "typeCheckmark", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "activity", ignore = true)
    @Mapping(target = "achievementId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateToEntity(AchievementRequest request, @MappingTarget Achievement achievement);
}
