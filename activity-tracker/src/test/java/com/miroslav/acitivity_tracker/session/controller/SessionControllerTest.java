package com.miroslav.acitivity_tracker.session.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miroslav.acitivity_tracker.group.controller.GroupController;
import com.miroslav.acitivity_tracker.group.service.GroupService;
import com.miroslav.acitivity_tracker.session.dto.SessionRequest;
import com.miroslav.acitivity_tracker.session.dto.SessionResponse;
import com.miroslav.acitivity_tracker.session.service.SessionService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SessionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = SessionController.class)
public class SessionControllerTest {

    private static final String BASE_PATH = "/session";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private SessionService sessionService;

    private SessionResponse response1;
    private SessionResponse response2;
    private SessionRequest request;
    private Integer sessionId;

    @BeforeEach
    public void setUp(){
        response1 = new SessionResponse("session1", "info1", LocalDateTime.now(), LocalDateTime.now().plusDays(2), Timestamp.valueOf(LocalDateTime.now()));
        response2 = new SessionResponse("session2", "info2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), Timestamp.valueOf(LocalDateTime.now()));
        request = new SessionRequest("session1", "info1", Timestamp.valueOf(LocalDateTime.now()));
        sessionId = 1;
    }

    @Test
    public void findById_returnOk_and_Response() throws Exception {
        when(sessionService.findById(sessionId)).thenReturn(response1);

        mockMvc.perform(get(BASE_PATH + "/" + sessionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void findSession_returnOk_and_Response() throws Exception {
        when(sessionService.findSessionFromProfile(sessionId, 1)).thenReturn(response1);

        mockMvc.perform(get(BASE_PATH + "/1/" + sessionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void findAllSessions_returnOk_and_PageResponse() throws Exception {
        when(sessionService.findAllSessions(any(), any())).thenReturn(new PageImpl<>(List.of(response1, response2)));

        mockMvc.perform(get(BASE_PATH + "/all/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "createdAt")
                        .param("sort-direction", "asc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)));
    }

    @Test
    public void createSession_returnAccepted_and_Integer() throws Exception{
        when(sessionService.createSession(any(), any())).thenReturn(sessionId);

        mockMvc.perform(post(BASE_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(sessionId.toString()));
    }

    @Test
    public void createSessionWithTime_returnAccepted_and_Integer() throws Exception{
        when(sessionService.createSessionWithTime(any(), any())).thenReturn(sessionId);

        mockMvc.perform(post(BASE_PATH + "/input-time/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(sessionId.toString()));
    }

    @Test
    public void endSession_returnOk_and_Integer() throws Exception {
        when(sessionService.endSession(1, sessionId)).thenReturn(sessionId);

        mockMvc.perform(put(BASE_PATH + "/1/end-session/" + sessionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(sessionId.toString()));
    }

    @Test
    public void updateSession_returnOk_and_Integer() throws Exception{
        when(sessionService.updateSession(any(), any(), any())).thenReturn(sessionId);
        mockMvc.perform(put(BASE_PATH + "/1/" + sessionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(sessionId.toString()));
    }

    @Test
    public void addNote_returnOk_and_Integer() throws Exception{
        when(sessionService.addNote(1, sessionId, "note")).thenReturn(sessionId);

        mockMvc.perform(put(BASE_PATH + "/add-note/1/" + sessionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("note"))
                .andExpect(status().isOk())
                .andExpect(content().string(sessionId.toString()));
    }

    @Test
    public void deleteSession_returnOk() throws Exception {
        when(sessionService.deleteSession(1, sessionId)).thenReturn("Session deleted successfully");

        mockMvc.perform(delete(BASE_PATH + "/1/" + sessionId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Session deleted successfully"));
    }
}
