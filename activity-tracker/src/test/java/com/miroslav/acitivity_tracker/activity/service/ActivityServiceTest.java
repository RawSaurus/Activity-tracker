package com.miroslav.acitivity_tracker.activity.service;

import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapper;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapperImpl;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.activity.service.ActivityService;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.file.model.File;
import com.miroslav.acitivity_tracker.file.service.FileService;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ActivityServiceTest {

    @InjectMocks
    private ActivityService activityService;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserContext userContext;
    @Mock
    private FileService fileService;
    @Spy
    private ActivityMapper activityMapper = new ActivityMapperImpl();

    ActivityServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    private Profile profile;
    private User user;
    private Activity activity;
    private Activity activity2;
    @BeforeEach
    public void setUp(){
        profile = Profile.builder()
                .profileId(1)
                .username("admin")
                .activities(new ArrayList<>())
                .build();
        user = User.builder()
                .userId(1)
                .profile(profile)
                .email("test@email.com")
                .firstName("test")
                .lastName("user")
                .build();
        activity = Activity.builder()
                .activityId(1)
                .name("activity")
                .info("short info")
                .type("type")
                .profile(profile)
                .category(Category.SPORT)
                .build();
        activity2 = Activity.builder()
                .activityId(2)
                .name("activity 2")
                .info("info 2")
                .type("type")
                .category(Category.SPORT)
                .profile(profile)
                .build();
    }

    @Test
    public void should_find_by_id(){
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));

        ActivityResponse response = activityService.findById(1);

        assertNotNull(response);
        assertEquals(activityMapper.toResponse(activity), response);
    }

    @Test
    public void should_find_by_id_with_links(){

    }

    @Test
    public void should_find_in_user_library(){
        when(activityRepository.findActivityByActivityIdAndProfileProfileId(1, user.getUserId())).thenReturn(Optional.of(activity));
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        ActivityResponse response = activityService.findInUserLibrary(1);

        assertNotNull(response);
        assertEquals(activity.getName(), response.name());
    }

    @Test
    public void should_find_in_user_library_by_name(){
        when(activityRepository.findByProfileProfileIdAndName(user.getUserId(), "activity")).thenReturn(Optional.of(activity));
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        ActivityResponse response = activityService.findInUserLibraryByName("activity");

        assertNotNull(response);
        assertEquals(activity.getName(), response.name());
    }

    @Test
    public void should_find_all_and_return_page(){
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "name");
        Page<Activity> page = new PageImpl<>(List.of(activity, activity2), pageable, 2);

        when(activityRepository.findAllByProfileProfileId(any(), any())).thenReturn(page);
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        Page<ActivityResponse> response = activityService.findAll(pageable);

        assertNotNull(response.getContent());
        assertEquals("activity 2", response.getContent().get(1).name());
    }

    @Test
    public void should_find_all_and_return_page_with_links(){

    }

    @Test
    public void should_create_activity(){
        ActivityRequest request = new ActivityRequest("activity", "info", "type", "SPORT", true);

        when(profileRepository.findById(any())).thenReturn(Optional.of(profile));
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(activityRepository.findByProfileProfileIdAndName(any(), any())).thenReturn(Optional.empty());
        when(activityRepository.save(any())).thenReturn(activity);

        Integer createdActivity = activityService.createActivity(request);

        assertNotNull(createdActivity);
        assertEquals(1, createdActivity);
    }

    @Test
    public void should_throw_action_not_allowed_when_name_already_exists(){
        ActivityRequest request = new ActivityRequest("activity", "info", "type", "SPORT", true);

        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(any())).thenReturn(Optional.of(profile));
        when(activityRepository.findByProfileProfileIdAndName(any(), any())).thenReturn(Optional.of(activity));
        when(activityRepository.save(any())).thenReturn(activity);

        assertThrows(ActionNotAllowed.class,() -> activityService.createActivity(request));
    }

    @Test
    public void should_update_activity(){
        ActivityRequest request = new ActivityRequest("activity upd", "info upd", "type upd", "SPORT", false);

        when(activityRepository.findById(any())).thenReturn(Optional.of(activity));
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(activityRepository.save(activity)).thenReturn(activity);

        ActivityResponse res = activityService.updateActivity(1, request);

        assertNotNull(res);
        assertEquals("activity upd", res.name());
    }

    @Test
    public void should_add_image(){
        File file = File.builder()
                        .name("file")
                        .build();

        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));
        when(fileService.uploadFile(any())).thenReturn(file);
        when(activityRepository.save(activity)).thenReturn(activity);

        activityService.addImage(1, null);

        assertEquals(file.getName(), activity.getPicture().getName());
    }

    @Test
    public void should_delete_activity_by_id(){
//        activity.setAchievements(new ArrayList<>());
//
//        when(userContext.getAuthenticatedUser()).thenReturn(user);
//        when(profileRepository.findById(user.getUserId())).thenReturn(Optional.of(profile));
//
//        when(activityRepository.findById(activity.getActivityId())).thenReturn(Optional.of(activity));
//
//        String result = activityService.deleteActivityById(activity.getActivityId());
//
//        assertEquals("Activity deleted successfully", result);
//        verify(activityRepository, times(1)).delete(activity);
    }
}
