package com.miroslav.acitivity_tracker.template.service;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapper;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapperImpl;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.file.assembler.FileAssembler;
import com.miroslav.acitivity_tracker.file.service.FileService;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.template.dto.TemplateRequest;
import com.miroslav.acitivity_tracker.template.dto.TemplateResponse;
import com.miroslav.acitivity_tracker.template.mapper.TemplateMapper;
import com.miroslav.acitivity_tracker.template.mapper.TemplateMapperImpl;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.template.repository.TemplateRepository;
import com.miroslav.acitivity_tracker.template.service.TemplateService;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TemplateServiceTest {

    @Mock
    private UserContext userContext;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private TemplateRepository templateRepository;
    @Mock
    private FileService fileService;
    @Mock
    private FileAssembler<TemplateResponse> fileAssembler;
    @Spy
    private ActivityMapper activityMapper = new ActivityMapperImpl();
    @Spy
    private TemplateMapper templateMapper = new TemplateMapperImpl();

    @InjectMocks
    private TemplateService templateService;

    public TemplateServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    /**
     * findByIdWithLinks
     * findAllByIdWithLinks
     */

    User user;
    Profile profile;
    Template template1;
    Template template2;
    @BeforeEach
    public void setUp(){
        profile = Profile.builder()
                .profileId(1)
                .username("admin")
                .activities(new ArrayList<>())
                .build();

        user = User.builder()
                .profile(profile)
                .email("test@email.com")
                .firstName("test")
                .lastName("user")
                .build();

        template1 = Template.builder()
                .templateId(1)
                .name("template 1")
                .info("info 1")
                .type("type 1")
                .category(Category.SPORT)
                .achievements(new ArrayList<>())
                .profile(profile)
                .build();
        template2 = Template.builder()
                .templateId(2)
                .name("template 2")
                .info("info 2")
                .type("type 2")
                .category(Category.SPORT)
                .profile(profile)
                .achievements(new ArrayList<>())
                .build();
    }

    @Test
    public void should_find_template_by_id() {
//        Template template = new Template();
//        template.setTemplateId(1);
        when(templateRepository.findById(1)).thenReturn(Optional.of(template1));

        assertNotNull(templateService.findById(1));
        verify(templateRepository, times(1)).findById(1);
    }

//    @Test
//    public void should_find_template_by_id_with_links(){
//        EntityModel<TemplateResponse> model = EntityModel.of(templateMapper.toResponse(template1));
//
//        when(templateRepository.findById(any())).thenReturn(Optional.of(template1));
//        when(fileAssembler.addLinks(any(), any())).thenReturn()
//
//    }

    @Test
    public void should_find_all(){
        Pageable pageable = PageRequest.of(0,5, Sort.Direction.ASC, "name");
        Page<Template> page = new PageImpl<>(List.of(template1, template2),pageable,2);

        when(templateRepository.findAllByProfileProfileId(any(), any())).thenReturn(page);
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        Page<TemplateResponse> response = templateService.findAll(pageable);
        assertNotNull(response.getContent());
        assertEquals(2, response.getContent().size());
    }

    @Test
    public void should_find_all_templates_with_links(){
        Pageable pageable = PageRequest.of(0,5, Sort.Direction.ASC, "name");
        Page<Template> page = new PageImpl<>(List.of(template1, template2));

        when(templateRepository.findAllByProfileProfileId(any(), any())).thenReturn(page);
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        Page<TemplateResponse> result = templateService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("template 1", result.getContent().get(0).name());
        assertEquals("template 2", result.getContent().get(1).name());
        verify(templateRepository, times(1)).findAllByProfileProfileId(user.getUserId(), pageable);
    }

    @Test
    public void should_throw_exception_when_template_not_found() {
        when(templateRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> templateService.findById(1));
    }

    @Test
    public void should_create_new_template() {
        User user = new User();
        user.setUserId(1);
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        Profile profile = new Profile();
        profile.setProfileId(1);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));

        TemplateRequest request = new TemplateRequest("Template Name", "Template Info", "sport", "SPORT");
        when(templateRepository.findByName("Template Name")).thenReturn(Optional.empty());

        Template template = new Template();
        template.setTemplateId(1);
        when(templateRepository.save(any(Template.class))).thenReturn(template);

        Integer templateId = templateService.createNewTemplate(request);

        assertEquals(1, templateId);
        verify(templateRepository, times(1)).save(any(Template.class));
    }

//    @Test
//    public void should_throw_exception_when_template_name_exists() {
//        TemplateRequest request = new TemplateRequest("Template Name", "Template Info", "sport", "SPORT");
//        when(templateRepository.findByName("Template Name")).thenReturn(Optional.of(template1));
//
//        assertThrows(DataIntegrityViolationException.class, () -> templateService.createNewTemplate(request));
//    }

    @Test
    public void should_copy_from_activity_and_create_template() {
        Activity activity = new Activity();
        activity.setActivityId(1);
        activity.setAchievements(new ArrayList<>());

        Achievement achievement = new Achievement();
        achievement.setName("Achievement Name");
        activity.getAchievements().add(achievement);

        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));

        Template template = new Template();
        template.setTemplateId(1);
        when(templateRepository.save(any(Template.class))).thenReturn(template);

        Integer templateId = templateService.postTemplateFromExistingActivity(1);

        assertEquals(1, templateId);
        verify(templateRepository, times(1)).save(any(Template.class));
    }

    @Test
    public void should_throw_exception_when_activity_not_found() {
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> templateService.postTemplateFromExistingActivity(1));
    }

    @Test
    public void should_update_template(){
        TemplateRequest request = new TemplateRequest("template 1 upd", "info 1", "type 1", "SPORT");

        when(profileRepository.findById(any())).thenReturn(Optional.of(profile));
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(templateRepository.findById(any())).thenReturn(Optional.of(template1));
        when(templateRepository.save(any())).thenReturn(template1);

        TemplateResponse response = templateService.updateTemplate(1, request);

        assertNotNull(response);
        assertEquals("template 1 upd", response.name());
    }

    @Test
    public void should_delete_template() {
        User user = new User();
        user.setUserId(1);
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        Profile profile = new Profile();
        profile.setProfileId(1);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));

        Template template = new Template();
        template.setTemplateId(1);
        template.setProfile(profile);
        template.setAchievements(new ArrayList<>());
        when(templateRepository.findById(1)).thenReturn(Optional.of(template));

        String result = templateService.deleteTemplate(1);

        assertEquals("Template deleted successfully", result);
        verify(templateRepository, times(1)).delete(template);
    }

    @Test
    public void should_throw_exception_when_deleting_template_not_owned_by_user() {
        User user = new User();
        user.setUserId(1);
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        Profile userProfile = Profile.builder()
                .profileId(user.getUserId())
                .user(user)
                .build();

        Profile profile = new Profile();
        profile.setProfileId(2);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));

        Template template = new Template();
        template.setTemplateId(1);
        template.setProfile(profile);
        when(templateRepository.findById(1)).thenReturn(Optional.of(template));

        assertThrows(ActionNotAllowed.class, () -> templateService.deleteTemplate(1));
    }
}
