package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapper;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapperImpl;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.activity.service.ActivityService;
import com.miroslav.acitivity_tracker.security.UserContext;
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
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MockitoSettings(strictness = Strictness.LENIENT)
//@ContextConfiguration(classes = ActivityTrackerApplication.class)
public class ActivityServiceTest {

    @InjectMocks
    private ActivityService activityService;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserContext userContext;
    @Spy
    private ActivityMapper activityMapper = new ActivityMapperImpl();

    private Activity activity;
    @BeforeEach
    public void setUp(){
        activity = Activity.builder()
                .name("activity")
                .info("short info")
                .type("type")
                .category(Category.SPORT)
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
    public void should_find_all_by_category(){

        Activity activity = Activity.builder()
                .name("activity")
                .info("short info")
                .type("type")
                .category(Category.SPORT)
                .build();

//        when(activityRepository.findAllByCategory("sport")).thenReturn(List.of(activity));
//
//        List<Activity> list = activityService.findAllByCategory("sport").stream().map(activityMapper::toEntity).toList();
//
//        assertEquals(activity.getName(), list.get(0).getName());
    }

//    @Test
//    public void should_succesfully_find_private_activity(){
//
//    }

    @Test
    public void should_find_activity_in_user_library(){
//        when(activityRepository.findActivityByActivityIdAndProfileProfileId(activity.))
    }


    @Test
    public void should_save_an_activity(){
        Profile profile = Profile.builder()
                .profileId(1)
                .username("admin")
                .activities(new ArrayList<>())
                .build();

        User authUser = User.builder()
                        .profile(profile)
                        .email("test@email.com")
                        .firstName("test")
                        .lastName("user")
                        .build();

        when(userContext.getAuthenticatedUser()).thenReturn(authUser);
        when(profileRepository.findById(userContext.getAuthenticatedUser().getUserId()))
                .thenReturn(Optional.of(profile));
        when(activityRepository.save(activity)).thenReturn(activity);

//        Integer activityId = activityService.createActivity(activityMapper.toRequest(activity));
        Activity savedActivity = activityRepository.save(activity);

        assertNotNull(savedActivity);
        assertEquals(savedActivity.getActivityId(), 1);
    }
}
