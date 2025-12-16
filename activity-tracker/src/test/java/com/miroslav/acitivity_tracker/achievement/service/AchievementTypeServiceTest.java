package com.miroslav.acitivity_tracker.achievement.service;

import com.miroslav.acitivity_tracker.achievement.dto.AchievementRequest;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponseV2;
import com.miroslav.acitivity_tracker.achievement.mapper.AchievementMapper;
import com.miroslav.acitivity_tracker.achievement.mapper.AchievementMapperImpl;
import com.miroslav.acitivity_tracker.achievement.model.*;
import com.miroslav.acitivity_tracker.achievement.repository.*;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.file.assembler.FileAssembler;
import com.miroslav.acitivity_tracker.file.service.FileService;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AchievementTypeServiceTest {

    @Mock
    private UserContext userContext;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private AmountAchievementRepository amountARepository;
    @Mock
    private DailyAchievementRepository dailyARepository;
    @Mock
    private DailyAchievementCalendarRepository dailyCalendar;
    @Mock
    private GoalAchievementRepository goalARepository;
    @Mock
    private FileService fileService;
    @Mock
    private FileAssembler<AchievementResponseV2> fileAssembler;
    @Spy
    private AchievementMapper achievementMapper = new AchievementMapperImpl();
    @InjectMocks
    private AchievementTypeService achievementTypeService;

    AchievementTypeServiceTest(){
        MockitoAnnotations.openMocks(this);
    }
    private Achievement achievement1;
    private Achievement achievement2;
    private GoalAchievement goalAchievement;
    private DailyAchievement dailyAchievement;
    private AmountAchievement amountAchievement;
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
                .type(Type.GOAL)
                .activity(activity)
                .build();
        achievement2 = Achievement.builder()
                .achievementId(2)
                .name("achievement2")
                .info("other info")
                .type(Type.DAILY)
                .activity(activity)
                .build();
        goalAchievement = GoalAchievement.builder()
                .typeAchievementId(achievement1.getAchievementId())
                .setXPGain(50)
                .totalXP(500)
                .achievement(achievement1)
                .deadline(Date.valueOf(LocalDate.now().plusDays(2)))
                .build();
        dailyAchievement = DailyAchievement.builder()
                .typeAchievementId(achievement2.getAchievementId())
                .setXPGain(50)
                .totalXP(500)
                .achievement(achievement2)
                .biggestStreak(10)
                .currentStreak(2)
                .build();
        amountAchievement = AmountAchievement.builder()
                .typeAchievementId(3)
                .setXPGain(50)
                .totalXP(500)
                .achievement(achievement1)
                .unit("weight")
                .build();
    }

    private TypeSuperclassRepository getType(Type type){
        if(type == Type.GOAL){
            return goalARepository;
        }else if(type == Type.DAILY){
            return dailyARepository;
        }else if(type == Type.AMOUNT){
            return amountARepository;
        }
        return null;
    }

    @Test
    public void findById_shouldReturnResponseV2() {
        AchievementResponseV2 expectedResponse = new AchievementResponseV2();
        expectedResponse.setName(achievement1.getName());
        expectedResponse.setInfo(achievement1.getInfo());
        expectedResponse.setType(achievement1.getType());
        expectedResponse.setXp(achievement1.getXp());
        expectedResponse.setTypeData(goalAchievement);

        when(achievementRepository.findById(achievement1.getAchievementId())).thenReturn(Optional.of(achievement1));
        when(getType(achievement1.getType()).findById(achievement1.getAchievementId())).thenReturn(Optional.of(goalAchievement));

        AchievementResponseV2 actualResponse = achievementTypeService.findById(achievement1.getAchievementId());

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getName()).isEqualTo(expectedResponse.getName());
        assertThat(actualResponse.getInfo()).isEqualTo(expectedResponse.getInfo());
        assertThat(actualResponse.getType()).isEqualTo(expectedResponse.getType());
    }

    @Test
    public void getFromActivity_shouldReturnResponseV2(){
        AchievementResponseV2 expectedResponse = new AchievementResponseV2();
        expectedResponse.setName(achievement1.getName());
        expectedResponse.setInfo(achievement1.getInfo());
        expectedResponse.setType(achievement1.getType());
        expectedResponse.setXp(achievement1.getXp());
        expectedResponse.setTypeData(goalAchievement);

        when(achievementRepository.findPublicById(achievement1.getAchievementId(), activity.getActivityId())).thenReturn(Optional.of(achievement1));
        when(goalARepository.findById(achievement1.getAchievementId())).thenReturn(Optional.of(goalAchievement));

        AchievementResponseV2 actualResponse = achievementTypeService.getFromActivity(achievement1.getAchievementId(), activity.getActivityId());

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getName()).isEqualTo(expectedResponse.getName());
        assertThat(actualResponse.getInfo()).isEqualTo(expectedResponse.getInfo());
        assertThat(actualResponse.getType()).isEqualTo(expectedResponse.getType());
    }

    @Test
    public void getAllFromActivity_shouldReturnListOfResponseV2(){
        List<Achievement> achievements = List.of(achievement1, achievement2);

        when(achievementRepository.findAllPublicById(activity.getActivityId())).thenReturn(achievements);
        when(getType(achievement1.getType()).findById(achievement1.getAchievementId())).thenReturn(Optional.of(goalAchievement));
        when(getType(achievement2.getType()).findById(achievement2.getAchievementId())).thenReturn(Optional.of(dailyAchievement));

        List<AchievementResponseV2> response = achievementTypeService.getAllFromActivity(activity.getActivityId());

        assertThat(response).isNotNull();
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getName()).isEqualTo(achievement1.getName());
        assertThat(response.get(0).getType()).isEqualTo(achievement1.getType());
        assertThat(response.get(1).getName()).isEqualTo(achievement2.getName());
        assertThat(response.get(1).getType()).isEqualTo(achievement2.getType());
    }

    @Test
    public void getAllFromActivityPage_shouldReturnPageOfResponseV2(){
        Pageable pageable = PageRequest.of(0,2);
        Page<Achievement> page = new PageImpl<>(List.of(achievement1, achievement2), pageable, 2);

        when(achievementRepository.findAllByActivityActivityId(activity.getActivityId(), pageable)).thenReturn(page);
        when(getType(achievement1.getType()).findById(achievement1.getAchievementId())).thenReturn(Optional.of(goalAchievement));
        when(getType(achievement2.getType()).findById(achievement2.getAchievementId())).thenReturn(Optional.of(dailyAchievement));

        Page<AchievementResponseV2> response = achievementTypeService.getAllFromActivityPage(activity.getActivityId(), pageable);

        assertThat(response.getContent()).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0).getName()).isEqualTo(achievement1.getName());
        assertThat(response.getContent().get(0).getType()).isEqualTo(achievement1.getType());
        assertThat(response.getContent().get(1).getName()).isEqualTo(achievement2.getName());
        assertThat(response.getContent().get(1).getType()).isEqualTo(achievement2.getType());
    }

    @Test
    public void shouldCreateGoalAchievement(){
        AchievementRequest request = new AchievementRequest("Goal Achievement", "Description", "GOAL", 10, 100, "unit", any(java.util.Date.class));
        Date deadline = Date.valueOf(LocalDate.now().plusDays(5));
        int setXpGain = 50;

        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(user.getUserId())).thenReturn(Optional.of(profile));
        when(activityRepository.findById(activity.getActivityId())).thenReturn(Optional.of(activity));
        when(achievementRepository.save(any())).thenReturn(achievement1);
        when(goalARepository.save(any(GoalAchievement.class))).thenAnswer(invocation -> {
            GoalAchievement savedGoal = invocation.getArgument(0);
            savedGoal.setTypeAchievementId(1);
            return savedGoal;
        });

        Integer achievementId = achievementTypeService.createGoalAchievement(request, activity.getActivityId());

        assertThat(achievementId).isNotNull();
        assertThat(achievementId).isEqualTo(1);
    }

    @Test
    public void shouldCreateDailyAchievement(){
        AchievementRequest request = new AchievementRequest("Goal Achievement", "Description", "DAILY", 10, 100, "unit", any(java.util.Date.class));
        int setXpGain = 50;

        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(user.getUserId())).thenReturn(Optional.of(profile));
        when(activityRepository.findById(activity.getActivityId())).thenReturn(Optional.of(activity));
        when(achievementRepository.save(any())).thenReturn(achievement1);
        when(dailyARepository.save(any(DailyAchievement.class))).thenAnswer(invocation -> {
            DailyAchievement savedDaily = invocation.getArgument(0);
            savedDaily.setTypeAchievementId(1);
            return savedDaily;
        });

        Integer achievementId = achievementTypeService.createDailyAchievement(request, activity.getActivityId());

        assertThat(achievementId).isNotNull();
        assertThat(achievementId).isEqualTo(1);
    }

    @Test
    public void shouldCreateAmountAchievement(){
        AchievementRequest request = new AchievementRequest("Goal Achievement", "Description", "AMOUNT", 10, 100, "unit", any(java.util.Date.class));
        int setXpGain = 50;

        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(user.getUserId())).thenReturn(Optional.of(profile));
        when(activityRepository.findById(activity.getActivityId())).thenReturn(Optional.of(activity));
        when(achievementRepository.save(any())).thenReturn(achievement1);
        when(amountARepository.save(any(AmountAchievement.class))).thenAnswer(invocation -> {
            AmountAchievement savedAmount = invocation.getArgument(0);
            savedAmount.setTypeAchievementId(1);
            return savedAmount;
        });

        Integer achievementId = achievementTypeService.createAmountAchievement(request, activity.getActivityId());

        assertThat(achievementId).isNotNull();
        assertThat(achievementId).isEqualTo(1);
    }

    @Test
    public void shouldDeleteAchievement(){
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(user.getUserId())).thenReturn(Optional.of(profile));
        when(achievementRepository.findById(achievement1.getAchievementId())).thenReturn(Optional.of(achievement1));
        doNothing().when(goalARepository).deleteById(achievement1.getAchievementId());
        doNothing().when(achievementRepository).delete(achievement1);
        doNothing().when(fileService).deleteFile(anyString());

        achievementTypeService.deleteAchievement(achievement1.getAchievementId());

        verify(goalARepository, times(1)).deleteById(achievement1.getAchievementId());
        verify(achievementRepository, times(1)).delete(achievement1);
    }


}
