package com.miroslav.acitivity_tracker.achievement;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.achievement.model.Type;
import com.miroslav.acitivity_tracker.achievement.repository.AchievementRepository;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AchievementRepositoryTest {

    //TODO rewrite as mocks and use when().thenReturn()
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ProfileRepository profileRepository;

    private Achievement achievement1;
    private Achievement achievement2;
    private Activity activity1;
    private Profile profile;

    @BeforeAll
    public void setUp(){
        profile = Profile.builder()
                .profileId(2)
                .username("username")
                .activities(new ArrayList<>())
                .build();
        activity1 = Activity.builder()
                .name("activity")
                .info("activity info")
                .achievements(new ArrayList<>())
                .isPrivate(false)
                .build();
        achievement1 = Achievement.builder()
                .name("achievement")
                .info("info")
                .type(Type.DAILY)
                .build();
        achievement2 = Achievement.builder()
                .name("another a")
                .info("other info")
                .type(Type.ONE_TIME)
                .build();

        activity1.getAchievements().add(achievement1);
        activity1.getAchievements().add(achievement2);
        profile.getActivities().add(activity1);
        profileRepository.save(profile);
//        activityRepository.save(activity1);
//       achievementRepository.save(achievement1);
//       achievementRepository.save(achievement2);
    }

    @Test
    public void should_successfully_find_public_achievement(){
        Optional<Achievement> foundAchievement = achievementRepository.findPublicById(1, 1);

        assertTrue(foundAchievement.isPresent());
        assertEquals(foundAchievement.get().getName(), achievement1.getName());
    }

    @Test
    public void should_successfully_find_all_achievements_from_public_activity(){

        List<Achievement> foundList = achievementRepository.findAllPublicById(1);

        assertNotNull(foundList);
        assertEquals(2, foundList.size());
        assertEquals(achievement1.getName(), foundList.get(0).getName());
    }

    @Test
    public void should_find_from_profile(){
        Optional<Achievement> foundAchievement = achievementRepository.findFromProfile(1,1, 2);

        assertTrue(foundAchievement.isPresent());
        assertEquals(achievement1.getName(), foundAchievement.get().getName());
    }

    @Test
    public void should_save_achievement(){
        Achievement achievementToSave = Achievement.builder()
                .name("achievement")
                .info("info")
                .build();

        Achievement savedAchievement = achievementRepository.save(achievementToSave);

        assertNotNull(savedAchievement);
        assertEquals(achievementToSave.getName(), savedAchievement.getName());
    }

}
