package com.miroslav.acitivity_tracker.group.mapper;

import com.miroslav.acitivity_tracker.group.dto.GroupRequest;
import com.miroslav.acitivity_tracker.group.dto.GroupResponse;
import com.miroslav.acitivity_tracker.group.model.Group;
import org.mapstruct.*;

import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "groupId", ignore = true)
    @Mapping(target = "activities", ignore = true)
    Group toEntity(GroupRequest request);

    GroupResponse toResponse(Group group);

    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "groupId", ignore = true)
    @Mapping(target = "activities", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateToEntity(@MappingTarget Group group, GroupRequest request);
}
