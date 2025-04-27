package com.miroslav.acitivity_tracker.calendar.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.miroslav.acitivity_tracker.achievement.controller.AchievementController;
import com.miroslav.acitivity_tracker.activity.service.ActivityService;
import com.miroslav.acitivity_tracker.calendar.assembler.EventAssembler;
import com.miroslav.acitivity_tracker.calendar.dto.EventRequest;
import com.miroslav.acitivity_tracker.calendar.dto.EventResponse;
import com.miroslav.acitivity_tracker.calendar.model.EventType;
import com.miroslav.acitivity_tracker.calendar.service.CalendarService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CalendarController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = CalendarController.class)
public class CalendarControllerTest {
    private static final String BASE_PATH = "/calendar";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CalendarService calendarService;
    @MockitoBean
    private EventAssembler eventAssembler;

    private EventResponse response1;
    private EventResponse response2;
    private EventRequest request;
    private Integer eventId;

    @BeforeEach
    public void setUp(){
        response1 = new EventResponse("event1", LocalDateTime.now(), LocalDateTime.now().plusDays(2), EventType.PLAN, null);
        response2 = new EventResponse("event2", LocalDateTime.now(), LocalDateTime.now().plusDays(3), EventType.ACHIEVEMENT_DONE, 1);
        request = new EventRequest("event1", LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        eventId = 1;
    }

    @Test
    public void getEvent_returnOk_and_ModelResponse() throws Exception {
        when(calendarService.getEvent(eventId)).thenReturn(response2);

        mockMvc.perform(get(BASE_PATH + "/" + eventId)
                .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response2.name())));
    }

    @Test
    public void findAll_returnOk_and_PageModelResponse() throws Exception {
        Page<EntityModel<EventResponse>> page = new PageImpl<>(List.of(EntityModel.of(response1), EntityModel.of(response2)));
        when(calendarService.findAll(any())).thenReturn(page);

        mockMvc.perform(get(BASE_PATH + "/all")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "startTime")
                        .param("sort-direction", "asc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)));
    }

    @Test
    public void findAllInTimePeriod_returnOk_and_PageModelResponse() throws Exception {
        Page<EntityModel<EventResponse>> page = new PageImpl<>(List.of(EntityModel.of(response1), EntityModel.of(response2)));
        when(calendarService.findAllInTimePeriod(any(), any(), any())).thenReturn(page);

        mockMvc.perform(get(BASE_PATH + "/time-period")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .param("start", LocalDateTime.now().toString())
                        .param("end", LocalDateTime.now().plusDays(2).toString())
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "startTime")
                        .param("sort-direction", "asc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)));
    }

    @Test
    public void findAllByType_returnOk_and_PageModelResponse() throws Exception {
        Page<EntityModel<EventResponse>> page = new PageImpl<>(List.of(EntityModel.of(response1)));
        when(calendarService.findAllByType(any(), any())).thenReturn(page);

        mockMvc.perform(get(BASE_PATH + "/type")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .param("type", "PLAN")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "startTime")
                        .param("sort-direction", "asc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)));
    }

    @Test
    public void findAllInTimePeriodByType_returnOk_and_PageModelResponse() throws Exception {
        Page<EntityModel<EventResponse>> page = new PageImpl<>(List.of(EntityModel.of(response1)));
        when(calendarService.findAllInTimePeriodByType(any(), any(), any(), any())).thenReturn(page);

        mockMvc.perform(get(BASE_PATH + "/type-&-time-period")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .param("start", LocalDateTime.now().toString())
                        .param("end", LocalDateTime.now().plusDays(2).toString())
                        .param("type", "PLAN")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "startTime")
                        .param("sort-direction", "asc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)));
    }

    @Test
    public void createPlan_returnOk_and_Int() throws Exception {
        when(calendarService.createPlan(request)).thenReturn(eventId);

        mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(eventId.toString()));
    }

    @Test
    public void updateEvent_returnOk_and_Integer() throws Exception{
        when(calendarService.updateEvent(eventId, request)).thenReturn(eventId);

        mockMvc.perform(put(BASE_PATH + "/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(eventId.toString()));
    }

    @Test
    public void deleteEvent_returnNoContent() throws Exception {
        doNothing().when(calendarService).deleteEvent(eventId);

        mockMvc.perform(delete(BASE_PATH + "/" + eventId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
