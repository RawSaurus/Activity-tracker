package com.miroslav.acitivity_tracker.session.mapper;

import com.miroslav.acitivity_tracker.session.dto.SessionRequest;
import com.miroslav.acitivity_tracker.session.dto.SessionResponse;
import com.miroslav.acitivity_tracker.session.model.Session;
import org.mapstruct.*;

import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "start", ignore = true)
    @Mapping(target = "sessionId", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "finish", ignore = true)
    @Mapping(target = "duration", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "activity", ignore = true)
    Session toEntity(SessionRequest request);
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "sessionId", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "activity", ignore = true)
    Session toEntity(SessionResponse response);
    SessionRequest toRequest(Session session);
    SessionResponse toResponse(Session session);
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "start", ignore = true)
    @Mapping(target = "sessionId", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "finish", ignore = true)
    @Mapping(target = "duration", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "activity", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    void updateToEntity(SessionRequest request, @MappingTarget Session session);

}
