package com.miroslav.acitivity_tracker.activity.repository;

import com.miroslav.acitivity_tracker.RepositoryTestConfig;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {RepositoryTestConfig.class})
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Activity activity;
    private Activity activity2;
    private Profile profile;
    @BeforeEach
    public void setUp(){
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setFirstName("first");
        user.setLastName("last");
        User savedUser = testEntityManager.persist(user);
        Profile profileToSave = Profile.builder()
                .profileId(savedUser.getUserId())
                .user(savedUser)
                .username("username")
                .build();
        profile = testEntityManager.persist(profileToSave);
        activity = Activity.builder()
                .name("activity")
                .info("info")
                .category(Category.SPORT)
                .profile(profile)
                .creator("creator")
                .isPrivate(true)
                .profile(profile)
                .build();
        activity2 = Activity.builder()
                .name("other activity")
                .info("info2")
                .category(Category.ACTIVITY)
                .creator("creator")
                .isPrivate(false)
                .profile(profile)
                .build();

        activityRepository.save(activity);
        activityRepository.save(activity2);
    }

    @AfterEach
    public void tearDown(){
        activityRepository.deleteAll();
    }

    @Test
    public void should_find_activity_by_name(){
        Optional<Activity> foundActivity = activityRepository.findByName("activity");

        assertNotNull(foundActivity.get());
        assertEquals(activity.getName(), foundActivity.get().getName());
    }

    @Test
    public void should_successfully_find_activity_list_by_name(){
        List<Activity> foundList = activityRepository.findAllByName("activity");

        assertNotNull(foundList);
        assertEquals(activity.getName(), foundList.get(0).getName());
        assertThrows(IndexOutOfBoundsException.class, ()->foundList.get(1));
    }

    @Test
    public void should_find_page_by_profile_id(){

        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "name");

        Page<Activity> foundPage = activityRepository.findAllByProfileProfileId(profile.getProfileId(), pageable);

        assertNotNull(foundPage.getContent());
        assertEquals(2, foundPage.getContent().size());
        assertEquals("activity", foundPage.getContent().get(0).getName());
    }

//    @Test
//    public void should_successfully_find_list_of_activities_by_category(){
//        List<Activity> foundList = activityRepository.findAllByCategory(Category.SPORT);
//
//        assertNotNull(foundList);
//        assertEquals(activity.getCategory(), foundList.get(0).getCategory());
//        assertThrows(IndexOutOfBoundsException.class, ()->foundList.get(1));
//    }

    @Test
    public void should_find_by_profile_id_and_name(){

        Optional<Activity> foundActivity = activityRepository.findByProfileProfileIdAndName(profile.getProfileId(), "activity");

        assertNotNull(foundActivity);
        assertTrue(foundActivity.isPresent());
        assertEquals(activity.getActivityId(), foundActivity.get().getActivityId());
        assertEquals(profile.getProfileId(), foundActivity.get().getProfile().getProfileId());
        assertEquals("activity", foundActivity.get().getName());
    }

    @Test
    public void should_find_activity_by_activityId_and_profileId(){

        Optional<Activity> foundActivity = activityRepository.findActivityByActivityIdAndProfileProfileId(activity.getActivityId(),  profile.getProfileId());

        assertNotNull(foundActivity);
        assertTrue(foundActivity.isPresent());
        assertEquals("activity", foundActivity.get().getName());
    }

//    @Test
//    public void should_find_successfully_activity_by_name_and_creatorId(){
//        Optional<Activity> foundActivity = activityRepository.findActivityByNameAndCreatorId(
//                activity.getName(),
//                activity.getCreatorId());
//
//        assertNotNull(foundActivity);
//    }


//    @Test
//    public void should_successfully_find_activity_by_activityId_and_not_private(){
//        List<Activity> foundList = activityRepository.findInMarketByName("other activity");
//
//        assertNotNull(foundList);
//        assertEquals("other activity", foundList.get(0).getName());
//        assertThrows(IndexOutOfBoundsException.class, ()->foundList.get(1));
//    }
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
