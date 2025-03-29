package com.miroslav.acitivity_tracker.calendar.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.miroslav.acitivity_tracker.calendar.assembler.EventAssembler;
import com.miroslav.acitivity_tracker.calendar.dto.EventRequest;
import com.miroslav.acitivity_tracker.calendar.dto.EventResponse;
import com.miroslav.acitivity_tracker.calendar.module.Event;
import com.miroslav.acitivity_tracker.calendar.module.EventType;
import com.miroslav.acitivity_tracker.calendar.service.CalendarService;
import com.miroslav.acitivity_tracker.util.enums.EnumValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
@PreAuthorize("hasAuthority('USER')")
public class CalendarController {

    private final CalendarService calendarService;
    private final EventAssembler eventAssembler;

    @GetMapping("/{event-id}")
    public ResponseEntity<EntityModel<EventResponse>> getEvent(@PathVariable("event-id") Integer eventId){
        EntityModel<EventResponse> model = EntityModel.of(calendarService.getEvent(eventId));
        model.add(linkTo(methodOn(CalendarController.class).getEvent(eventId)).withSelfRel());
        eventAssembler.addLinks(model);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EntityModel<EventResponse>>> findAll(){
//        CollectionModel<EntityModel<EventResponse>> collection = CollectionModel.of(calendarService.findAll());
//        eventAssembler.addLinks(collection);
        return ResponseEntity.ok(calendarService.findAll());
    }

    @GetMapping("/time-period")
    public ResponseEntity<List<EntityModel<EventResponse>>> findAllInTimePeriod(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end){
//        CollectionModel<EntityModel<EventResponse>> collection = CollectionModel.of(calendarService.findAllInTimePeriod(start, end));
//        eventAssembler.addLinks(collection);
        return ResponseEntity.ok(calendarService.findAllInTimePeriod(start, end));
    }

    @GetMapping("/type")
    public ResponseEntity<List<EntityModel<EventResponse>>> findAllByType(@RequestParam("type") @Valid @EnumValidator(enumClazz = EventType.class) String type) {
//        CollectionModel<EntityModel<EventResponse>> collection = CollectionModel.of(calendarService.findAllByType(type));
//        eventAssembler.addLinks(collection);
        return ResponseEntity.ok(calendarService.findAllByType(type));
    }

    @GetMapping("/type-&-time-period")
    public ResponseEntity<List<EntityModel<EventResponse>>> findAllInTimePeriodByType(
            @RequestParam("start") LocalDateTime start,
            @RequestParam("end") LocalDateTime end,
            @RequestParam("type") @Valid @EnumValidator(enumClazz = EventType.class)String type
    ){
//        CollectionModel<EntityModel<EventResponse>> collection = CollectionModel.of(calendarService.findAllInTimePeriodByType(start, end, type));
//        eventAssembler.addLinks(collection);
        return ResponseEntity.ok(calendarService.findAllInTimePeriodByType(start, end, type));
    }


    @PostMapping
    public ResponseEntity<Integer> createPlan(@RequestBody EventRequest request){
        return ResponseEntity.ok(calendarService.createPlan(request));
    }

    @PutMapping("/{event-id}")
    public ResponseEntity<Integer> updateEvent(@PathVariable("event-id") Integer eventId, @RequestBody EventRequest request){
        return ResponseEntity.ok(calendarService.updateEvent(eventId, request));
    }

    @DeleteMapping("/{event-id}")
    public ResponseEntity deleteEvent(@PathVariable("event-id")Integer eventId){
        calendarService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
