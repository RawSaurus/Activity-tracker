package com.miroslav.acitivity_tracker.achievement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementRequest;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponse;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponseV2;
import com.miroslav.acitivity_tracker.achievement.model.Type;
import com.miroslav.acitivity_tracker.achievement.service.AchievementService;
import com.miroslav.acitivity_tracker.achievement.service.AchievementTypeService;
import com.miroslav.acitivity_tracker.file.controller.FileController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AchievementController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = AchievementController.class)
public class AchievementControllerTest {
    private final String BASE_PATH = "/achievement";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private AchievementService achievementService;
    @MockitoBean
    private AchievementTypeService achievementTypeService;

    AchievementResponse response1;
    AchievementResponse response2;
    AchievementResponseV2 responseV2;
    AchievementRequest request;

    @BeforeEach
    public void setUp() {
        response1 = new AchievementResponse( "Achievement 1", "Description 1", Type.GOAL, 100);
        response2 = new AchievementResponse( "Achievement 2", "Description 2", Type.GOAL, 200);
        request = new AchievementRequest("New Achievement", "New Description", "GOAL");
        responseV2 = new AchievementResponseV2();
        responseV2.setName("Achievement 3");
        responseV2.setInfo("Description 3");
        responseV2.setType(Type.GOAL);
    }

    @Test
    public void findAchievementById_returnOk() throws Exception {
        when(achievementService.findById(1,1)).thenReturn(response1);

        mockMvc.perform(get(BASE_PATH + "/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void findById_returnOk_and_achievementResponseV2() throws Exception {
        when(achievementTypeService.findById(any())).thenReturn(responseV2);

        mockMvc.perform(get(BASE_PATH + "/find-one/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(responseV2.getName())));
    }

    @Test
    public void getFromActivity_returnOk_and_AchievementResponseV2() throws Exception {
        when(achievementTypeService.getFromActivity(any(), any())).thenReturn(responseV2);

        mockMvc.perform(get(BASE_PATH + "/get-from-activity/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(responseV2.getName())));
    }

    @Test
    public void getAllFromActivity_returnOk_and_listOfResponseV2() throws Exception {
        when(achievementTypeService.getAllFromActivity(any())).thenReturn(List.of(responseV2));

        mockMvc.perform(get(BASE_PATH + "/get-all-from-activity/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].name", Matchers.is(responseV2.getName())));
    }

    @Test
    public void findAll_returnOk_andPageOfResponseV2() throws Exception {
        when(achievementTypeService.getAllFromActivityPage(any(), any())).thenReturn(new PageImpl<>(List.of(responseV2)));

        mockMvc.perform(get(BASE_PATH + "/all/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", Matchers.is(1)));
    }

    @Test
    public void findByIdWithLinks_returnOk_and_EntityModelOfResponseV2() throws Exception {
        EntityModel<AchievementResponseV2> model = EntityModel.of(responseV2);
        model.add(linkTo(methodOn(FileController.class).downloadFile("test-code")).withRel("get-picture"));
        model.add(linkTo(methodOn(FileController.class).updateFile("test-code", null)).withRel("update-picture"));
        model.add(linkTo(methodOn(FileController.class).deleteFile("test-code")).withRel("delete-picture"));

        when(achievementTypeService.findByIdWithLinks(any())).thenReturn(model);

        mockMvc.perform(get(BASE_PATH + "/links/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createGoalAchievement_returnOk_givenValidRequest() throws Exception {
        when(achievementTypeService.createGoalAchievement(any(AchievementRequest.class), anyInt(), any(Date.class), anyInt())).thenReturn(1);

        mockMvc.perform(post(BASE_PATH + "/goal-achievement/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("deadline", LocalDateTime.now().plusDays(2).toString())
                        .param("setXpGain", "50"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void createDailyAchievement_returnOk_givenValidRequest() throws Exception {
        when(achievementTypeService.createDailyAchievement(any(AchievementRequest.class), anyInt(), anyInt())).thenReturn(1);

        mockMvc.perform(post(BASE_PATH + "/daily-achievement/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("setXpGain", "50"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void createAmountAchievement_returnOk_givenValidRequest() throws Exception {
        when(achievementTypeService.createAmountAchievement(any(AchievementRequest.class), anyInt(), anyInt(), anyString())).thenReturn(1);

        mockMvc.perform(post(BASE_PATH + "/amount-achievement/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .param("setXpGain", "50")
                        .param("unit", "test-unit"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void deleteAchievement_returnOk() throws Exception {
        doNothing().when(achievementTypeService).deleteAchievement(anyInt());

        mockMvc.perform(delete(BASE_PATH + "/delete-achievement/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAchievement_returnOkAndResponse() throws Exception {
        when(achievementService.updateAchievement(1, 1, request)).thenReturn(1);

        mockMvc.perform(put(BASE_PATH + "/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(content().string("1"));
    }

    @Test
    public void markFinished_returnOk() throws Exception {
        doNothing().when(achievementService).markFinished(1);

        mockMvc.perform(patch(BASE_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addImage_returnOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test-name.txt", "text/plain", "content".getBytes());
        doNothing().when(achievementTypeService).addImage(any(), any());

        mockMvc.perform(multipart(HttpMethod.PATCH,BASE_PATH + "/image/1")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAchievement_returnOkWithString() throws Exception {
        when(achievementService.deleteAchievement(1, 1)).thenReturn("Achievement deleted successfully");

        mockMvc.perform(delete(BASE_PATH + "/1/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Achievement deleted successfully"));
    }
}
