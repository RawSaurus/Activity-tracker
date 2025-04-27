package com.miroslav.acitivity_tracker.group.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miroslav.acitivity_tracker.file.controller.FileController;
import com.miroslav.acitivity_tracker.file.service.FileService;
import com.miroslav.acitivity_tracker.group.dto.GroupRequest;
import com.miroslav.acitivity_tracker.group.dto.GroupResponse;
import com.miroslav.acitivity_tracker.group.service.GroupService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = GroupController.class)
public class GroupControllerTest {

    private static final String BASE_PATH = "/group";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private GroupService groupService;

    private GroupResponse response1;
    private GroupResponse response2;
    private GroupRequest request;
    private Integer groupId;

    @BeforeEach
    public void setUp(){
        response1 = new GroupResponse("group1");
        response2 = new GroupResponse("group2");
        request = new GroupRequest("group1");
        groupId = 1;
    }

    @Test
    public void getAllGroups_returnOk_and_PageResponse() throws Exception {
        when(groupService.findById(groupId)).thenReturn(response1);

        mockMvc.perform(get(BASE_PATH + "/" + groupId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void findAllActivitiesByGroup_returnOk_and_ListResponse() throws Exception {
        when(groupService.getAllGroups(any())).thenReturn(new PageImpl<>(List.of(response1, response2)));

        mockMvc.perform(get(BASE_PATH + "/all")
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
    public void createGroup_returnAccepted_and_String()throws Exception{
        when(groupService.createGroup(request)).thenReturn(response1.name());

        mockMvc.perform(post(BASE_PATH + "/create-group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(response1.name()));
    }

    @Test
    public void addActivityToGroup_returnAccepted_and_Response() throws Exception {
        when(groupService.addActivityToGroup(groupId, 1)).thenReturn(response1);

        mockMvc.perform(put(BASE_PATH + "/add-to-group/" + groupId + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void removeActivityFromGroup_returnOk_and_Response() throws Exception {
        when(groupService.removeActivityFromGroup(groupId, 1)).thenReturn(response1);

        mockMvc.perform(put(BASE_PATH + "/remove-from-group/" + groupId + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void updateGroup_returnOk_and_Response() throws Exception {
        when(groupService.updateGroup(groupId, request)).thenReturn(response1);

        mockMvc.perform(put(BASE_PATH + "/" + groupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(response1.name())));
    }

    @Test
    public void deleteGroup_returnOk_and_String() throws Exception {
        when(groupService.deleteGroup(groupId)).thenReturn("Group deleted successfully");

        mockMvc.perform(delete(BASE_PATH + "/" + groupId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Group deleted successfully"));
    }
}
