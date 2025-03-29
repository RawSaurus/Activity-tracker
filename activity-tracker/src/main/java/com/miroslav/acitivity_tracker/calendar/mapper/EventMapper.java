package com.miroslav.acitivity_tracker.calendar.mapper;

import com.miroslav.acitivity_tracker.calendar.dto.EventRequest;
import com.miroslav.acitivity_tracker.calendar.dto.EventResponse;
import com.miroslav.acitivity_tracker.calendar.module.Event;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "linkId", ignore = true)
    @Mapping(target = "eventId", ignore = true)
    Event toEntity(EventRequest request);

    EventResponse toResponse(Event event);

    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "linkId", ignore = true)
    @Mapping(target = "eventId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateToEntity(@MappingTarget Event event, EventRequest request);
}
