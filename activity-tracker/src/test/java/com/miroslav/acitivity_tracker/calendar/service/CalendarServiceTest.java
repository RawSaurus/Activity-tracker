package com.miroslav.acitivity_tracker.calendar.service;

import com.miroslav.acitivity_tracker.calendar.assembler.EventAssembler;
import com.miroslav.acitivity_tracker.calendar.dto.EventRequest;
import com.miroslav.acitivity_tracker.calendar.dto.EventResponse;
import com.miroslav.acitivity_tracker.calendar.mapper.EventMapper;
import com.miroslav.acitivity_tracker.calendar.model.Event;
import com.miroslav.acitivity_tracker.calendar.model.EventType;
import com.miroslav.acitivity_tracker.calendar.repository.EventRepository;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.group.model.Group;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CalendarServiceTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserContext userContext;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private EventAssembler eventAssembler;
    @Spy
    private EventMapper eventMapper;
    @InjectMocks
    private CalendarService calendarService;


    CalendarServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    private User user;
    private Profile profile;
    private Event event1;
    private Event event2;
    @BeforeEach
    public void setUp(){
        user = new User();
        user.setUserId(1);

        profile = new Profile();
        profile.setProfileId(1);
        profile.setUser(user);

        event1 = Event.builder()
                .eventId(1)
                .name("Event1")
                .profile(profile)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(2))
                .type(EventType.PLAN)
                .build();
        event2 = Event.builder()
                .eventId(2)
                .name("Event2")
                .profile(profile)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(2))
                .type(EventType.PLAN)
                .build();
    }

    @Test
    public void should_find_event_by_id() {

        when(eventRepository.findByEventIdAndProfileProfileId(anyInt(), anyInt())).thenReturn(Optional.of(event1));
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(eventMapper.toResponse(event1)).thenReturn(new EventResponse(event1.getName(), event1.getStart(), event1.getEnd(), EventType.PLAN, null));

        EventResponse response = calendarService.getEvent(1);

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo(event1.getName());
    }

    @Test
    public void should_throw_exception_when_event_not_found() {
        when(eventRepository.findById(999)).thenReturn(Optional.empty());
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        assertThatThrownBy(() -> calendarService.getEvent(999)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void should_find_all_and_return_page_entity_model_response(){

    }

    @Test
    public void should_create_event_plan() {
        EventRequest request = new EventRequest("New Event", LocalDateTime.now(), LocalDateTime.now().plusDays(2).plusHours(1));
        Event newEvent = Event.builder()
                        .eventId(3)
                        .name(request.name())
                        .profile(profile)
                        .build();

        when(profileRepository.findById(any())).thenReturn(Optional.of(profile));
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(eventRepository.save(any(Event.class))).thenReturn(newEvent);

        Integer response = calendarService.createPlan(request);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(3);
    }

//    @Test
//    public void should_delete_event() {
//        when(eventRepository.findById(1)).thenReturn(Optional.of(event1));
//        when(userContext.getAuthenticatedUser()).thenReturn(user);
//
//        assertThatThrownBy(() -> calendarService.deleteEvent(1)).isNull();
//        verify(eventRepository, times(1)).delete(event1);
//    }

    @Test
    public void should_throw_exception_when_deleting_event_not_owned_by_user() {
        Profile otherProfile = new Profile();
        otherProfile.setProfileId(2);
        event1.setProfile(otherProfile);
        when(eventRepository.findById(1)).thenReturn(Optional.of(event1));
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        assertThatThrownBy(() -> calendarService.deleteEvent(1)).isInstanceOf((ActionNotAllowed.class));
    }
}
