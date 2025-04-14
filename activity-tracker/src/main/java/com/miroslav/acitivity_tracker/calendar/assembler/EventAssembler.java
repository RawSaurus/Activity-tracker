package com.miroslav.acitivity_tracker.calendar.assembler;

import com.miroslav.acitivity_tracker.achievement.controller.AchievementController;
import com.miroslav.acitivity_tracker.calendar.controller.CalendarController;
import com.miroslav.acitivity_tracker.calendar.dto.EventResponse;
import com.miroslav.acitivity_tracker.calendar.model.EventType;
import com.miroslav.acitivity_tracker.session.controller.SessionController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EventAssembler implements SimpleRepresentationModelAssembler<EventResponse>{

    @Override
    public void addLinks(EntityModel<EventResponse> resource) {

//        resource.add(linkTo(methodOn(CalendarController.class).getEvent(resource.getContent().getEventId())).withSelfRel());

        if(resource.getContent().type()== EventType.ACHIEVEMENT_DONE){
            resource.add(linkTo(methodOn(AchievementController.class).findById(resource.getContent().linkId())).withRel("achievement-done"));
        }else if(resource.getContent().type()== EventType.SESSION) {
            resource.add(linkTo(methodOn(SessionController.class).findById(resource.getContent().linkId())).withRel("session"));
        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<EventResponse>> resources) {

        resources.add(linkTo(methodOn(CalendarController.class).findAll(null, null, null, null)).withSelfRel());
    }
}
