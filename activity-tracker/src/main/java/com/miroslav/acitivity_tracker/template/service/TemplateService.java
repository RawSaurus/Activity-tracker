package com.miroslav.acitivity_tracker.template.service;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.template.dto.TemplateRequest;
import com.miroslav.acitivity_tracker.template.dto.TemplateResponse;
import com.miroslav.acitivity_tracker.template.mapper.TemplateMapper;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.template.repository.TemplateRepository;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class TemplateService {
    private final UserContext userContext;
    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;
    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;

    public TemplateResponse findById(Integer templateId){
        return templateMapper.toResponse(templateRepository.findById(templateId).orElseThrow(()-> new EntityNotFoundException("Template not found")));
    }

    public Integer postTemplateFromExistingActivity(Integer activityId){
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity was not found"));

        //TODO validation/exception handling ?
        Template template = templateMapper.toEntity(activity);
        template.setAchievements(new ArrayList<>());
        for(Achievement a : activity.getAchievements()){
            Achievement achievement = Achievement.builder()
                    .name(a.getName())
                    .type(a.getType())
                    .info(a.getInfo())
                    .picture(a.getPicture())
                    .build();
            template.getAchievements().add(achievement);
        }
        return templateRepository.save(template).getTemplateId();
    }

    public Integer createNewTemplate(TemplateRequest request){
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        if(templateRepository.findByName(request.name()).isPresent()){
            throw new DataIntegrityViolationException("There is already a template with this name");
        }
        Template template = templateMapper.toEntity(request);
        template.setProfile(profile);
        template.setCreator(userContext.getAuthenticatedUser().getUsername());


        return templateRepository.save(template).getTemplateId();

    }
    public TemplateResponse updateTemplate(Integer templateId, TemplateRequest request){
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("Template not found"));

        if(!template.getProfile().getProfileId().equals(profile.getProfileId())){
            throw new ActionNotAllowed("You are not allowed to change this template");
        }

        templateMapper.updateToEntity(template, request);

        return templateMapper.toResponse(templateRepository.save(template));
    }
    public String deleteTemplate(Integer templateId){
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("Template not found"));

        if(!template.getProfile().getProfileId().equals(profile.getProfileId())){
            throw new ActionNotAllowed("You are not allowed to change this template");
        }

        templateRepository.delete(template);
        return "Template deleted successfully";
    }
}
