package com.miroslav.acitivity_tracker.template.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miroslav.acitivity_tracker.ActivityTrackerApplication;
import com.miroslav.acitivity_tracker.ControllerTestConfig;
import com.miroslav.acitivity_tracker.RepositoryTestConfig;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.security.Config;
import com.miroslav.acitivity_tracker.template.dto.TemplateRequest;
import com.miroslav.acitivity_tracker.template.dto.TemplateResponse;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.template.service.TemplateService;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void findAll() throws Exception {
        when(templateService.findAll(any())).thenReturn(new PageImpl<>(List.of(response1, response2)));

        mockMvc.perform(get(BASE_PATH + "/find-all")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "name")
                .param("sort-direction", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createNewTemplate_returnOk_givenValidRequest() throws Exception {
        TemplateRequest request = new TemplateRequest("Template 1", "Description 1", "type", "SPORT");
        Mockito.when(templateService.createNewTemplate(any())).thenReturn(1);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
