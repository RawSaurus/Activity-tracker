package com.miroslav.acitivity_tracker.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miroslav.acitivity_tracker.file.controller.FileController;
import com.miroslav.acitivity_tracker.user.dto.ProfileResponse;
import com.miroslav.acitivity_tracker.user.dto.UserRequest;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.service.ProfileService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ProfileController.class)
public class ProfileControllerTest {

    private static final String BASE_PATH = "/profile";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ProfileService profileService;

    private ProfileResponse response;
    private Profile profile;
    private UserRequest request;
    private Integer profileId;

    @BeforeEach
    public void setUp(){
        response = new ProfileResponse("username");
        User user = User.builder()
                .userId(1)
                .username("username")
                .firstName("first")
                .lastName("last")
                .email("email")
                .password("password")
                .roles(new ArrayList<>())
                .build();
        profile = Profile.builder()
                .profileId(1)
                .username("username")
                .user(user)
                .build();
        request = new UserRequest("first", "last", "email", "password", "USER");
        response = new ProfileResponse("username");
        profileId = 1;
    }

    @Test
    public void getProfile_returnOk_and_Profile() throws Exception {
        when(profileService.getProfile(profileId)).thenReturn(profile);

        mockMvc.perform(get(BASE_PATH + "/" + profileId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", Matchers.is(profile.getUsername())));
    }
     @Test
    public void getProfileByMail_returnOk_and_Profile() throws Exception {
         when(profileService.getProfileByMail("email")).thenReturn(profile);

         mockMvc.perform(get(BASE_PATH + "/mail/" + request.email())
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.username", Matchers.is(profile.getUsername())));
    }
     @Test
    public void getOwnProfile_returnOk_and_ProfileResponse() throws Exception {
         when(profileService.getOwnProfile()).thenReturn(response);

         mockMvc.perform(get(BASE_PATH)
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.username", Matchers.is(profile.getUsername())));

    }
     @Test
    public void getOwnProfileWithLinks_returnOk_and_ModelProfileResponse() throws Exception {
         String fileCode = "test-code";
         EntityModel<ProfileResponse> model = EntityModel.of(response);
         model.add(linkTo(methodOn(FileController.class).downloadFile(fileCode)).withRel("get-picture"));
         model.add(linkTo(methodOn(FileController.class).updateFile(fileCode, null)).withRel("update-picture"));
         model.add(linkTo(methodOn(FileController.class).deleteFile(fileCode)).withRel("delete-picture"));

         when(profileService.getProfileWithLinks(profileId)).thenReturn(model);

         mockMvc.perform(get(BASE_PATH + "/links/" + profileId)
                         .contentType(MediaTypes.HAL_JSON_VALUE))
                 .andExpect(status().isOk())
                 .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON));
    }
     @Test
    public void createProfile_returnOk_and_Integer() throws Exception {
         when(profileService.createProfile(request)).thenReturn(profileId);

         mockMvc.perform(post(BASE_PATH)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(request)))
                 .andExpect(status().isOk())
                 .andExpect(content().string(profileId.toString()));
    }
     @Test
    public void disableAccount_returnOk_and_Integer() throws Exception{
         when(profileService.disableAccount(profileId)).thenReturn(profileId);

         mockMvc.perform(put(BASE_PATH + "/disable/" + profileId)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(request)))
                 .andExpect(status().isOk())
                 .andExpect(content().string(profileId.toString()));
    }

     @Test
    public void addImage_returnAccepted() throws Exception {
         MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "someBytes".getBytes());
         doNothing().when(profileService).addImage(any());

         mockMvc.perform(multipart(HttpMethod.PATCH, BASE_PATH)
                         .file(file)
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isAccepted());
    }

     @Test
    public void deleteAccount_returnOk_and_String() throws Exception {
         when(profileService.deleteAccount(profileId)).thenReturn("Account deleted successfully");

         mockMvc.perform(delete(BASE_PATH + "/" + profileId)
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(content().string("Account deleted successfully"));
    }

     @Test
    public void deleteOwnAccount_returnOk_and_String() throws Exception {
         when(profileService.deleteOwnAccount()).thenReturn("Account deleted successfully");

         mockMvc.perform(delete(BASE_PATH + "/delete" )
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(content().string("Account deleted successfully"));
    }
}
