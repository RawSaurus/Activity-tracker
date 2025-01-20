package com.miroslav.acitivity_tracker.template;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapper;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapperImpl;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.template.mapper.TemplateMapper;
import com.miroslav.acitivity_tracker.template.mapper.TemplateMapperImpl;
import com.miroslav.acitivity_tracker.template.repository.TemplateRepository;
import com.miroslav.acitivity_tracker.template.service.TemplateService;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

public class TemplateServiceTest {

    @Mock
    private UserContext userContext;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private TemplateRepository templateRepository;
    @Spy
    private ActivityMapper activityMapper = new ActivityMapperImpl();
    @Spy
    private TemplateMapper templateMapper = new TemplateMapperImpl();

    @InjectMocks
    private TemplateService templateService;

    @Test
    public void should_copy_from_activity_and_create_template(){
        Activity activity = Activity.builder()
                .name("activity")
                .info("info")
                .type("type")
                .build();
        Achievement achievement = Achievement.builder()
                .name("name")
                .info("info")
                .build();

        activity.getAchievements().add(achievement);

    }
}
