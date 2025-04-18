package com.miroslav.acitivity_tracker.achievement.repository;

import com.miroslav.acitivity_tracker.RepositoryTestConfig;
import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.achievement.model.Type;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class AchievementRepositoryTest {

    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Achievement achievement1;
    private Achievement achievement2;
    private Activity activity;
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
        Activity activityToSave = Activity.builder()
                .name("activity")
                .info("info")
                .category(Category.SPORT)
                .profile(profile)
                .creator("creator")
                .isPrivate(false)
                .profile(profile)
                .build();
        activity = testEntityManager.persist(activityToSave);
        achievement1 = Achievement.builder()
                .name("achievement1")
                .info("info")
                .type(Type.DAILY)
                .activity(activity)
                .build();
        achievement2 = Achievement.builder()
                .name("achievement2")
                .info("other info")
                .activity(activity)
                .build();

        achievementRepository.saveAll(List.of(achievement1, achievement2));
    }

    @AfterEach
    public void tearDown(){
        achievementRepository.deleteAll();
    }

    @Test
    public void should_successfully_find_public_achievement(){
        Optional<Achievement> foundAchievement = achievementRepository.findPublicById(achievement1.getAchievementId(), activity.getActivityId());

        assertTrue(foundAchievement.isPresent());
        assertEquals(foundAchievement.get().getName(), achievement1.getName());
    }

    @Test
    public void should_successfully_find_all_achievements_from_public_activity(){

        List<Achievement> foundList = achievementRepository.findAllPublicById(activity.getActivityId());

        assertNotNull(foundList);
        assertEquals(2, foundList.size());
        assertEquals(achievement1.getName(), foundList.get(0).getName());
    }

    @Test
    @DisplayName("findAllByActivityActivityId should return page of achievements")
    public void findAllByActivityActivityId_shouldReturnPageOfAchievements_givenActivityIdAndPageable(){
        Pageable pageable = PageRequest.of(0, 2);

        Page<Achievement> page = achievementRepository.findAllByActivityActivityId(activity.getActivityId(), pageable);

        assertThat(page.getContent()).isNotNull();
        assertThat(page.getContent().size()).isEqualTo(2);
        assertThat(page.getContent().get(0).getName()).isEqualTo(achievement1.getName());
    }

    @Test
    public void should_find_from_profile(){
        Optional<Achievement> foundAchievement = achievementRepository.findFromProfile(achievement1.getAchievementId(), activity.getActivityId(), profile.getProfileId());

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
