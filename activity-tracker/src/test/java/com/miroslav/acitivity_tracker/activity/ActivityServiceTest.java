package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapper;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapperImpl;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.activity.service.ActivityService;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@MockitoSettings(strictness = Strictness.LENIENT)
//@ContextConfiguration(classes = ActivityTrackerApplication.class)
public class ActivityServiceTest {

    @InjectMocks
    private ActivityService activityService;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Spy
    private ActivityMapper activityMapper = new ActivityMapperImpl();

//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void should_find_by_id(){

        Activity activity = Activity.builder()
                .name("activity")
                .info("short info")
                .type("type")
                .category(Category.SPORT)
                .rating(2.0)
                .downloads(20)
                .build();

        Activity savedActivity = activityRepository.save(activity);

        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));
        System.out.println(activity.getName());
        ActivityResponse response = activityService.findById(1);
        System.out.println(activity.toString()+"\n" + response.toString());

        assertEquals(activityMapper.toResponse(activity), response);
        assertNotNull(response);

    }

    @Test
    public void should_find_all_by_category(){

        Activity activity = Activity.builder()
                .name("activity")
                .info("short info")
                .type("type")
                .category(Category.SPORT)
                .build();

        activityRepository.save(activity);

        when(activityRepository.findAllByCategory("sport")).thenReturn(List.of(activity));


        List<Activity> list = activityService.findAllByCategory("sport").stream().map(activityMapper::toEntity).toList();

        assertEquals(activity.getName(), list.get(1).getName());

        System.out.println(activityService.findAllByCategory("sport"));
    }

    @Test
    public void should_save_an_activity(){
        Profile profile = Profile.builder()
                .profileId(1)
                .username("admin")
                .build();


        Integer activity = activityService.createActivity(activityMapper.toRequest(Activity.builder()
                .activityId(1)
                .name("activity")
                .info("short info")
                .type("type")
                .build()));

        assertEquals(activity, 1);
    }
}
