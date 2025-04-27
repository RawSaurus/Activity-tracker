package com.miroslav.acitivity_tracker.template.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.file.controller.FileController;
import com.miroslav.acitivity_tracker.template.dto.TemplateRequest;
import com.miroslav.acitivity_tracker.template.dto.TemplateResponse;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.template.service.TemplateService;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TemplateController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {TemplateController.class})
//@DirtiesContext(classMode = BEFORE_CLASS)
public class TemplateControllerTest {

    private final String BASE_PATH = "/template";

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TemplateService templateService;
    @Autowired
    private ObjectMapper objectMapper;

    User user;
    Profile profile;
    Template template1;
    Template template2;
    TemplateRequest request;
    TemplateResponse response1;
    TemplateResponse response2;
    @BeforeEach
    public void setUp(){
        profile = Profile.builder()
                .profileId(1)
                .username("admin")
                .activities(new ArrayList<>())
                .build();
        user = User.builder()
                .profile(profile)
                .email("test@email.com")
                .firstName("test")
                .lastName("user")
                .build();
        template1 = Template.builder()
                .templateId(1)
                .name("template 1")
                .info("info 1")
                .type("type 1")
                .category(Category.SPORT)
                .achievements(new ArrayList<>())
                .profile(profile)
                .build();
        template2 = Template.builder()
                .templateId(2)
                .name("template 2")
                .info("info 2")
                .type("type 2")
                .category(Category.SPORT)
                .profile(profile)
                .achievements(new ArrayList<>())
                .build();
        request = new TemplateRequest("request", "req info", "req type", "SPORT");
        response1 = new TemplateResponse("name1", "info1", "type1", Category.SPORT);
        response2 = new TemplateResponse("name2", "info2", "type2", Category.SPORT);

    }

    @Test
    public void findTemplateById_returnOk() throws Exception {
        when(templateService.findById(template1.getTemplateId())).thenReturn(response1);

        mockMvc.perform(get(BASE_PATH + "/" + template1.getTemplateId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response1)));
    }

    @Test
    public void findAll_returnOk_andPageOfTemplateResponse() throws Exception {
        when(templateService.findAll(any())).thenReturn(new PageImpl<>(List.of(response1, response2)));

        mockMvc.perform(get(BASE_PATH + "/find-all")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "name")
                .param("sort-direction", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", CoreMatchers.is(2)));
    }

    @Test
    public void findTemplateWithLinks_returnOk_and_EntityModelOfTemplateResponse() throws Exception {
        String fileCode = "test-code";
        EntityModel<TemplateResponse> response = EntityModel.of(response1);
        response.add(linkTo(methodOn(FileController.class).downloadFile(fileCode)).withRel("get-picture"));
        response.add(linkTo(methodOn(FileController.class).updateFile(fileCode, null)).withRel("update-picture"));
        response.add(linkTo(methodOn(FileController.class).deleteFile(fileCode)).withRel("delete-picture"));

        when(templateService.findByIdWithLinks(template1.getTemplateId())).thenReturn(response);

        mockMvc.perform(get(BASE_PATH + "/links/" + template1.getTemplateId())
                .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON));
    }

    @Test
    public void findAllTemplatesWithLinks_returnOk_and_PageOfEntityModelOfResponse() throws Exception {
        String fileCode = "test-code";
        String fileCode2 = "test-code2";
        EntityModel<TemplateResponse> model = EntityModel.of(response1);
        model .add(linkTo(methodOn(FileController.class).downloadFile(fileCode)).withRel("get-picture"));
        model.add(linkTo(methodOn(FileController.class).updateFile(fileCode, null)).withRel("update-picture"));
        model.add(linkTo(methodOn(FileController.class).deleteFile(fileCode)).withRel("delete-picture"));
        EntityModel<TemplateResponse> model2 = EntityModel.of(response2);
        model2.add(linkTo(methodOn(FileController.class).downloadFile(fileCode2)).withRel("get-picture"));
        model2.add(linkTo(methodOn(FileController.class).updateFile(fileCode2, null)).withRel("update-picture"));
        model2.add(linkTo(methodOn(FileController.class).deleteFile(fileCode2)).withRel("delete-picture"));

        Page<EntityModel<TemplateResponse>> page = new PageImpl<>(List.of(model, model2));

        when(templateService.findAllWithLinks(any())).thenReturn(page);

        mockMvc.perform(get(BASE_PATH + "/links/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)));
    }

    @Test
    public void createNewTemplate_returnOk_givenValidRequest() throws Exception {
        TemplateRequest request = new TemplateRequest("Template 1", "Description 1", "type", "SPORT");
        Mockito.when(templateService.createNewTemplate(any())).thenReturn(1);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void postTemplateFromExistingTemplate_returnOk() throws Exception{
        when(templateService.postTemplateFromExistingActivity(1)).thenReturn(1);

        mockMvc.perform(post(BASE_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void updateTemplate_returnOkAndResponse() throws Exception {
        when(templateService.updateTemplate(template1.getTemplateId(), request)).thenReturn(response1);

        mockMvc.perform(put(BASE_PATH + "/" + template1.getTemplateId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void addImage_returnOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "someBytes".getBytes());
        doNothing().when(templateService).addImage(any(), any());

        mockMvc.perform(multipart(BASE_PATH + "/1")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTemplate_returnOkWithString() throws Exception {
        when(templateService.deleteTemplate(any())).thenReturn("Template deleted successfully");

        mockMvc.perform(delete(BASE_PATH + "/delete/1")
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Template deleted successfully"));
    }
}
