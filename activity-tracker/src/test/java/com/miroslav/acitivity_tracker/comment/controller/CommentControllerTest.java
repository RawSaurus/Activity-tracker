package com.miroslav.acitivity_tracker.comment.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.miroslav.acitivity_tracker.calendar.controller.CalendarController;
import com.miroslav.acitivity_tracker.calendar.service.CalendarService;
import com.miroslav.acitivity_tracker.comment.dto.CommentRequest;
import com.miroslav.acitivity_tracker.comment.dto.CommentResponse;
import com.miroslav.acitivity_tracker.comment.service.CommentService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = CommentController.class)
public class CommentControllerTest {

    private static final String BASE_PATH = "/comment";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CommentService commentService;

    private CommentResponse response1;
    private CommentResponse response2;
    private CommentRequest request;
    private Integer commentId;

    @BeforeEach
    public void setUp(){
        response1 = new CommentResponse("header1", "text1", 2);
        response2 = new CommentResponse("header2", "text2", 3);
        request = new CommentRequest("header1", "text1");
        commentId = 1;
    }

    @Test
    public void findById_returnOk_and_Response() throws Exception {
        when(commentService.findById(commentId)).thenReturn(response1);

        mockMvc.perform(get(BASE_PATH + "/" + commentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header", Matchers.is(response1.header())));
    }

    @Test
    public void findByTemplateId_returnOk_and_PageResponse() throws Exception {
        when(commentService.findAllByTemplateId(any(), any())).thenReturn(new PageImpl<>(List.of(response1, response2)));

        mockMvc.perform(get(BASE_PATH + "/template/1")
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
    public void createComment_returnOk_and_Integer() throws Exception{
        when(commentService.createComment(request, 1)).thenReturn(commentId);

        mockMvc.perform(post(BASE_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(commentId.toString()));
    }

    @Test
    public void updateComment_returnOk_and_Response() throws Exception{
        when(commentService.updateComment(commentId, request)).thenReturn(response1);

        mockMvc.perform(put(BASE_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header", Matchers.is(response1.header())));
    }

    @Test
    public void deleteComment_returnOk_and_String() throws Exception {
        when(commentService.deleteComment(commentId)).thenReturn("Comment deleted successfully");

        mockMvc.perform(delete(BASE_PATH + "/" + commentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment deleted successfully"));
    }
}
