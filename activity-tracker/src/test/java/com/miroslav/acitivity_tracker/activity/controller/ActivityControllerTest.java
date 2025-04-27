package com.miroslav.acitivity_tracker.activity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.service.ActivityService;
import com.miroslav.acitivity_tracker.file.controller.FileController;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.template.dto.TemplateResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ActivityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ActivityController.class)
public class ActivityControllerTest {

    private static final String BASE_PATH = "/activity";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ActivityService activityService;


    private ActivityResponse response1;
    private ActivityResponse response2;
    private ActivityRequest request;
    private Integer activityId;

    @BeforeEach
    public void setUp(){
        response1 = new ActivityResponse("activity1", "info1", "type1", Category.SPORT);
        response2 = new ActivityResponse("activity2", "info2", "type2", Category.SPORT);
        request = new ActivityRequest("activity1", "info1", "type1", "SPORT", false);
        activityId =  1;
    }

    @Test
    public void findById_returnOk_and_Response() throws Exception {
        when(activityService.findById(activityId)).thenReturn(response1);

        mockMvc.perform(get(BASE_PATH + "/" + activityId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void findInUserLibrary_returnOk_and_Response() throws Exception {
        when(activityService.findInUserLibrary(activityId)).thenReturn(response1);

        mockMvc.perform(get(BASE_PATH + "/library/" + activityId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void findInUserLibraryByName_returnOk_and_Response() throws Exception {
        when(activityService.findInUserLibraryByName(request.name())).thenReturn(response1);

        mockMvc.perform(get(BASE_PATH + "/name/" + request.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void findAll_returnOk_and_PageResponse() throws Exception {
        when(activityService.findAll(any())).thenReturn(new PageImpl<>(List.of(response1, response2)));

        mockMvc.perform(get(BASE_PATH + "/all")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "10")
                .param("sort", "name")
                .param("sort-direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", Matchers.is(2)));
    }
    @Test
    public void findByIdWithLinks_returnOk_and_ModelResponse() throws Exception {
        String fileCode = "test-code";
        EntityModel<ActivityResponse> response = EntityModel.of(response1);
        response.add(linkTo(methodOn(FileController.class).downloadFile(fileCode)).withRel("get-picture"));
        response.add(linkTo(methodOn(FileController.class).updateFile(fileCode, null)).withRel("update-picture"));
        response.add(linkTo(methodOn(FileController.class).deleteFile(fileCode)).withRel("delete-picture"));

        when(activityService.findByIdWithLinks(activityId)).thenReturn(response);

        mockMvc.perform(get(BASE_PATH + "/links/" + activityId)
                        .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON));
    }
    @Test
    public void findAllWithLinks_returnOk_and_PageModelResponse() throws Exception {
        String fileCode = "test-code";
        String fileCode2 = "test-code2";
        EntityModel<ActivityResponse> model = EntityModel.of(response1);
        model.add(linkTo(methodOn(FileController.class).downloadFile(fileCode)).withRel("get-picture"));
        model.add(linkTo(methodOn(FileController.class).updateFile(fileCode, null)).withRel("update-picture"));
        model.add(linkTo(methodOn(FileController.class).deleteFile(fileCode)).withRel("delete-picture"));
        EntityModel<ActivityResponse> model2 = EntityModel.of(response2);
        model2.add(linkTo(methodOn(FileController.class).downloadFile(fileCode2)).withRel("get-picture"));
        model2.add(linkTo(methodOn(FileController.class).updateFile(fileCode2, null)).withRel("update-picture"));
        model2.add(linkTo(methodOn(FileController.class).deleteFile(fileCode2)).withRel("delete-picture"));

        Page<EntityModel<ActivityResponse>> page = new PageImpl<>(List.of(model, model2));

        when(activityService.findAllWithLinks(any())).thenReturn(page);

        mockMvc.perform(get(BASE_PATH + "/all/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name")
                        .param("sort-direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)));
    }
    @Test
    public void createActivity_returnOk_and_Int() throws Exception {
        when(activityService.createActivity(request)).thenReturn(activityId);

        mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(activityId.toString()));
    }
    @Test
    public void updateActivity_returnAccepted_and_Response() throws Exception {
        when(activityService.updateActivity(activityId, request)).thenReturn(response1);

        mockMvc.perform(put(BASE_PATH + "/" + activityId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }
    @Test
    public void addImage_returnAccepted() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "someBytes".getBytes());
        doNothing().when(activityService).addImage(any(), any());

        mockMvc.perform(multipart(HttpMethod.PATCH,BASE_PATH + "/1")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
    @Test
    public void deleteActivityById_returnOk() throws Exception {
        when(activityService.deleteActivityById(activityId)).thenReturn("Activity deleted successfully");

        mockMvc.perform(delete(BASE_PATH + "/" + activityId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Activity deleted successfully"));
    }
}
