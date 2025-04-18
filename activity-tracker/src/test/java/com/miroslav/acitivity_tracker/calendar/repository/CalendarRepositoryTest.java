package com.miroslav.acitivity_tracker.calendar.repository;

import com.miroslav.acitivity_tracker.RepositoryTestConfig;
import com.miroslav.acitivity_tracker.calendar.model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class CalendarRepositoryTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Event event1;
    private Event event2;

    @BeforeEach
    public void setUp() {
        event1 = Event.builder()
                .name("Event1")
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(1).plusHours(2))
                .build();

        event2 = Event.builder()
                .name("Event2")
                .start(LocalDateTime.now().plusDays(2))
                .end(LocalDateTime.now().plusDays(2).plusHours(3))
                .build();

        testEntityManager.persistAndFlush(event1);
        testEntityManager.persistAndFlush(event2);
    }

    @AfterEach
    public void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    public void findById_shouldReturnEvent_whenEventExists() {
        Optional<Event> foundEvent = eventRepository.findById(event1.getEventId());
        assertThat(foundEvent).isPresent();
        assertThat(foundEvent.get().getName()).isEqualTo(event1.getName());
    }

    @Test
    public void findById_shouldReturnEmpty_whenEventDoesNotExist() {
        Optional<Event> foundEvent = eventRepository.findById(999);
        assertThat(foundEvent).isEmpty();
    }

    @Test
    public void findAll_shouldReturnAllEvents() {
        List<Event> events = eventRepository.findAll();
        assertThat(events).hasSize(2).extracting(Event::getName).containsExactlyInAnyOrder("Event1", "Event2");
    }

    @Test
    public void save_shouldPersistEvent() {
        Event newEvent = Event.builder()
                .name("Event3")
                .start(LocalDateTime.now().plusDays(3))
                .end(LocalDateTime.now().plusDays(3).plusHours(1))
                .build();

        Event savedEvent = eventRepository.save(newEvent);
        assertThat(savedEvent.getEventId()).isNotNull();
        assertThat(savedEvent.getName()).isEqualTo("Event3");
    }

    @Test
    public void deleteById_shouldRemoveEvent() {
        eventRepository.deleteById(event1.getEventId());
        Optional<Event> deletedEvent = eventRepository.findById(event1.getEventId());
        assertThat(deletedEvent).isEmpty();
    }

    @Test
    public void deleteAll_shouldRemoveAllEvents() {
        eventRepository.deleteAll();
        List<Event> events = eventRepository.findAll();
        assertThat(events).isEmpty();
    }
}
