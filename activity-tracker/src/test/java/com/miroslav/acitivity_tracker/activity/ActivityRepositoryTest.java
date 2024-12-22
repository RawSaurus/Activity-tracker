package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.user.model.Profile;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    Activity activity;
    Activity activity2;
    Profile profile;
    @BeforeAll
    public void setUp(){
        profile = Profile.builder()
                .username("username")
                .profileId(3) // ID 1 and 2 already exists ind DB
                .build();
        activity = Activity.builder()
                .name("activity")
                .info("info")
                .category(Category.SPORT)
                .profile(profile)
                .creatorId(1)
                .creator("creator")
                .isPrivate(true)
                .build();
        activity2 = Activity.builder()
                .name("other activity")
                .info("info2")
                .category(Category.ACTIVITY)
                .creatorId(1)
                .creator("creator")
                .isPrivate(false)
                .build();

        activityRepository.save(activity);
        activityRepository.save(activity2);
    }

    @AfterAll
    public void tearDown(){
        activityRepository.delete(activity);
        activityRepository.delete(activity2);
    }

    @Test
    public void should_successfully_find_activity_list_by_name(){
        List<Activity> foundList = activityRepository.findAllByName("activity");

        assertNotNull(foundList);
        assertEquals(activity.getName(), foundList.get(0).getName());
        assertThrows(IndexOutOfBoundsException.class, ()->foundList.get(1));
    }

    @Test
    public void should_successfully_find_list_of_activities_by_category(){
        List<Activity> foundList = activityRepository.findAllByCategory(Category.SPORT);

        assertNotNull(foundList);
        assertEquals(activity.getCategory(), foundList.get(0).getCategory());
        assertThrows(IndexOutOfBoundsException.class, ()->foundList.get(1));
    }

    @Test
    public void should_find_activity_by_activityId_and_profileId(){
        //TODO duplicate key error when saving same profile to different activities - check connections between tables

        Optional<Activity> foundActivity = activityRepository.findActivityByActivityIdAndProfileProfileId(
                1,  3
        );
        assertNotNull(foundActivity);
        assertTrue(foundActivity.isPresent());
        assertEquals(1, foundActivity.get().getActivityId());
        assertEquals(3, foundActivity.get().getProfile().getProfileId());
    }

    @Test
    public void should_find_successfully_activity_by_name_and_creatorId(){
        Optional<Activity> foundActivity = activityRepository.findActivityByNameAndCreatorId(
                activity.getName(),
                activity.getCreatorId());

        assertNotNull(foundActivity);
    }

    @Test
    public void should_successfully_find_activity_by_activityId_isPrivate_is_true(){
        Optional<Activity> foundActivity = activityRepository.findActivityByActivityIdAndIsPrivate(1, true);

        assertNotNull(foundActivity);
        assertTrue(foundActivity.isPresent());
        assertEquals("activity", foundActivity.get().getName());
        assertTrue(foundActivity.get().isPrivate());
    }

    @Test
    public void should_successfully_find_activity_by_activityId_and_not_private(){
        List<Activity> foundList = activityRepository.findInMarketByName("other activity");

        assertNotNull(foundList);
        assertEquals("other activity", foundList.get(0).getName());
        assertThrows(IndexOutOfBoundsException.class, ()->foundList.get(1));
    }
    @Test
    public void should_save_activity(){
        Activity savedActivity = activityRepository.save(Activity.builder()
                .name("activity")
                .info("info")
                .category(Category.SPORT)
                .creator("creator")
                .isPrivate(true)
                .build());

        assertNotNull(savedActivity);
    }

    @Test
    public void should_delete_activity(){
        Activity activityToDelete = Activity.builder()
                .name("delete activity")
                .build();

        activityRepository.save(activityToDelete);

        activityRepository.deleteById(activityToDelete.getActivityId());
        Optional<Activity> returnActivity = activityRepository.findById(activityToDelete.getActivityId());
        assertTrue(returnActivity.isEmpty());
    }

}
