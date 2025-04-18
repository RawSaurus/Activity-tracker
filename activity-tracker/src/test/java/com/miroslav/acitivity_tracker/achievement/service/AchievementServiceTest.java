package com.miroslav.acitivity_tracker.achievement.service;

import com.miroslav.acitivity_tracker.achievement.dto.AchievementRequest;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponse;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponseWType;
import com.miroslav.acitivity_tracker.achievement.mapper.AchievementMapper;
import com.miroslav.acitivity_tracker.achievement.mapper.AchievementMapperImpl;
import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.achievement.model.Type;
import com.miroslav.acitivity_tracker.achievement.repository.AchievementRepository;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.calendar.repository.EventRepository;
import com.miroslav.acitivity_tracker.file.assembler.FileAssembler;
import com.miroslav.acitivity_tracker.file.service.FileService;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AchievementServiceTest {

    @Mock
    private UserContext userContext;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private AchievementRepository achievementRepository;
    @Spy
    private AchievementMapper achievementMapper = new AchievementMapperImpl();
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private FileService fileService;
    @Mock
    private FileAssembler<AchievementResponseWType> fileAssembler;
    @InjectMocks
    private AchievementService achievementService;

    AchievementServiceTest(){
        MockitoAnnotations.openMocks(this);
    }
    private Achievement achievement1;
    private Achievement achievement2;
    private Activity activity;
    private Profile profile;
    private User user;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setUserId(1);
        user.setEmail("email@gmail.com");
        user.setFirstName("first");
        user.setLastName("last");
        profile = Profile.builder()
                .profileId(1)
                .profileId(user.getUserId())
                .user(user)
                .username("username")
                .build();
        user.setProfile(profile);
        activity = Activity.builder()
                .activityId(1)
                .name("activity")
                .info("info")
                .category(Category.SPORT)
                .profile(profile)
                .creator("creator")
                .isPrivate(false)
                .profile(profile)
                .achievements(new ArrayList<>())
                .build();
        achievement1 = Achievement.builder()
                .achievementId(1)
                .name("achievement1")
                .info("info")
                .type(Type.DAILY)
                .activity(activity)
                .build();
        achievement2 = Achievement.builder()
                .achievementId(2)
                .name("achievement2")
                .info("other info")
                .activity(activity)
                .build();
    }

    @Test
    public void findById_shouldReturnAchievementResponse(){
        when(achievementRepository.findById(achievement1.getAchievementId())).thenReturn(Optional.of(achievement1));

        AchievementResponse response = achievementService.findById(activity.getActivityId(), achievement1.getAchievementId());

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo(achievement1.getName());
    }

    @Test
    public void createAchievement_shouldReturnInt_givenRequest(){
        AchievementRequest request = new AchievementRequest("new achievement", "info", "DAILY");
        Achievement newAchievement = new Achievement();
        newAchievement.setAchievementId(3);

        when(profileRepository.findById(profile.getProfileId())).thenReturn(Optional.of(profile));
        when(activityRepository.findById(activity.getActivityId())).thenReturn(Optional.of(activity));
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(achievementRepository.save(any())).thenReturn(newAchievement);

        Integer response = achievementService.createAchievement(request, activity.getActivityId());

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(newAchievement.getAchievementId());
    }

    @Test
    public void updateAchievement_shouldUpdateAchievement_givenRequest(){
        AchievementRequest request = new AchievementRequest("achievement upd", "info", "DAILY");

        when(achievementRepository.findFromProfile(achievement1.getAchievementId(), activity.getActivityId(), user.getUserId())).thenReturn(Optional.of(achievement1));
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(achievementRepository.save(any())).thenReturn(achievement1);

        Integer response = achievementService.updateAchievement(activity.getActivityId(), achievement1.getAchievementId(), request);

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(achievement1.getAchievementId());
        assertThat(achievement1.getName()).isEqualTo(request.name());
    }
}
