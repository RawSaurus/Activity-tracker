package com.miroslav.acitivity_tracker.calendar.service;

import com.miroslav.acitivity_tracker.calendar.assembler.EventAssembler;
import com.miroslav.acitivity_tracker.calendar.controller.CalendarController;
import com.miroslav.acitivity_tracker.calendar.dto.EventRequest;
import com.miroslav.acitivity_tracker.calendar.dto.EventResponse;
import com.miroslav.acitivity_tracker.calendar.mapper.EventMapper;
import com.miroslav.acitivity_tracker.calendar.model.Event;
import com.miroslav.acitivity_tracker.calendar.model.EventType;
import com.miroslav.acitivity_tracker.calendar.repository.EventRepository;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserContext userContext;
    private final EventAssembler eventAssembler;
    private final ProfileRepository profileRepository;

    public EventResponse getEvent(Integer eventId){
        return eventMapper.toResponse(eventRepository.findByEventIdAndProfileProfileId(eventId, userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found")));
    }

    public Page<EntityModel<EventResponse>> findAll(Pageable pageable){
        Page<Event> events = eventRepository.findAllByProfileProfileId(userContext.getAuthenticatedUser().getUserId(), pageable);
        return events.map(event -> {
            Integer id = event.getEventId();
            EntityModel<EventResponse> model = EntityModel.of(eventMapper.toResponse(event));
            model.add(linkTo(methodOn(CalendarController.class).getEvent(id)).withSelfRel());
            eventAssembler.addLinks(model);
            return model;
                }
        );
//        List<EntityModel<EventResponse>> list = new ArrayList<>();
//        for(Event e : eventRepository.findAllByProfileProfileId(userContext.getAuthenticatedUser().getUserId())){
//            Integer id = e.getEventId();
//            EntityModel<EventResponse> model = EntityModel.of(eventMapper.toResponse(e));
//            model.add(linkTo(methodOn(CalendarController.class).getEvent(id)).withSelfRel());
//            eventAssembler.addLinks(model);
//            list.add(model);
//        }
//        return list;

//        return eventRepository.findAllByProfileProfileId(userContext.getAuthenticatedUser().getUserId())
//                .stream()
//                .map(eventMapper::toResponse)
//                .map(e -> { EntityModel.of(e, linkTo(methodOn(CalendarController.class).getEvent(e.getEventId())).withSelfRel());
//                    eventAssembler.addLinks(e);
//                    return e;
//                })
//                .collect(Collectors.toList());
    }

    public Page<EntityModel<EventResponse>> findAllInTimePeriod(LocalDateTime start, LocalDateTime end, Pageable pageable){
        Page<Event> events = eventRepository.findAllByStartBetween(userContext.getAuthenticatedUser().getUserId(), start, end, pageable);

        return events.map(event -> {
            Integer id = event.getEventId();
            EntityModel<EventResponse> model = EntityModel.of(eventMapper.toResponse(event));
            model.add(linkTo(methodOn(CalendarController.class).getEvent(id)).withSelfRel());
            eventAssembler.addLinks(model);
            return model;
        });

//        List<EntityModel<EventResponse>> list = new ArrayList<>();
//        for(Event e : eventRepository.findAllByStartBetween(userContext.getAuthenticatedUser().getUserId(), start, end)){
//            Integer id = e.getEventId();
//            EntityModel<EventResponse> model = EntityModel.of(eventMapper.toResponse(e));
//            model.add(linkTo(methodOn(CalendarController.class).getEvent(id)).withSelfRel());
//            eventAssembler.addLinks(model);
//            list.add(model);
//        }
//        return list;
    }

    public Page<EntityModel<EventResponse>> findAllByType(String type, Pageable pageable){
        Page<Event> events = eventRepository.findAllByProfileProfileIdAndType(userContext.getAuthenticatedUser().getUserId(), EventType.valueOf(type), pageable);
        return events.map(event -> {
            Integer id = event.getEventId();
            EntityModel<EventResponse> model = EntityModel.of(eventMapper.toResponse(event));
            model.add(linkTo(methodOn(CalendarController.class).getEvent(id)).withSelfRel());
            eventAssembler.addLinks(model);
            return model;
        });
//        List<EntityModel<EventResponse>> list = new ArrayList<>();
//        for(Event e : eventRepository.findAllByProfileProfileIdAndType(userContext.getAuthenticatedUser().getUserId(), EventType.valueOf(type))){
//            Integer id = e.getEventId();
//            EntityModel<EventResponse> model = EntityModel.of(eventMapper.toResponse(e));
//            model.add(linkTo(methodOn(CalendarController.class).getEvent(id)).withSelfRel());
//            eventAssembler.addLinks(model);
//            list.add(model);
//        }
//        return list;
    }

    public Page<EntityModel<EventResponse>> findAllInTimePeriodByType(LocalDateTime start, LocalDateTime end, String type, Pageable pageable){
        Page<Event> events = eventRepository.findAllByStartBetweenAndType(userContext.getAuthenticatedUser().getUserId(), start, end, EventType.valueOf(type), pageable);
        return events.map(event -> {
            Integer id = event.getEventId();
            EntityModel<EventResponse> model = EntityModel.of(eventMapper.toResponse(event));
            model.add(linkTo(methodOn(CalendarController.class).getEvent(id)).withSelfRel());
            eventAssembler.addLinks(model);
            return model;
        });
//        List<EntityModel<EventResponse>> list = new ArrayList<>();
//        for(Event e : eventRepository.findAllByStartBetweenAndType(userContext.getAuthenticatedUser().getUserId(), start, end, EventType.valueOf(type))){
//            Integer id = e.getEventId();
//            EntityModel<EventResponse> model = EntityModel.of(eventMapper.toResponse(e));
//            model.add(linkTo(methodOn(CalendarController.class).getEvent(id)).withSelfRel());
//            eventAssembler.addLinks(model);
//            list.add(model);
//        }
//        return list;
    }

    public Integer createPlan(EventRequest request){
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

        Event event = Event.builder()
                .name(request.name())
                .start(request.start())
                .end(request.end())
                .type(EventType.PLAN)
                .profile(profile)
                .build();

        return eventRepository.save(event).getEventId();
    }

    public Integer updateEvent(Integer eventId, EventRequest request){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        if(!event.getProfile().getProfileId().equals(userContext.getAuthenticatedUser().getUserId())){
            throw new ActionNotAllowed("You cannot update this event");
        }

        eventMapper.updateToEntity(event, request);

        return eventRepository.save(event).getEventId();
    }

    public void deleteEvent(Integer eventId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        if(!event.getProfile().getProfileId().equals(userContext.getAuthenticatedUser().getUserId())){
            throw new ActionNotAllowed("You cannot delete this event");
        }

        eventRepository.delete(event);
    }
}
