package com.miroslav.acitivity_tracker.activity.service;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.file.assembler.FileAssembler;
import com.miroslav.acitivity_tracker.file.service.FileService;
import com.miroslav.acitivity_tracker.group.dto.GroupRequest;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapper;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.group.model.Group;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.group.repository.GroupRepository;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.template.dto.TemplateResponse;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final UserContext userContext;
    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final FileService fileService;
    private final FileAssembler<ActivityResponse> fileAssembler;

    //TODO create unit tests
    public ActivityResponse findById(Integer activityId) {
        return activityRepository.findById(activityId)
                .map(activityMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
    }

    //new
    public EntityModel<ActivityResponse> findByIdWithLinks(Integer activityId){
        Activity activity = activityRepository.findById(activityId).orElseThrow(()-> new EntityNotFoundException("Activity not found"));
        EntityModel<ActivityResponse> model = EntityModel.of(activityMapper.toResponse(activity));
        fileAssembler.addLinks(model, activity.getPicture().getFileCode());
        return model;
    }

//    public List<ActivityResponse> findAllByCategory(Category activityCategory) {
//        return activityRepository.findAllByCategory(activityCategory)
//                .stream()
//                .map(activityMapper::toResponse)
//                .collect(Collectors.toList());
//    }

    //TODO test
    public ActivityResponse findInUserLibrary(Integer activityId) {
        return activityRepository.findActivityByActivityIdAndProfileProfileId(activityId, userContext.getAuthenticatedUser().getUserId())
                .map(activityMapper::toResponse)
                .orElse(null);
    }

    //TODO test
    public ActivityResponse findInUserLibraryByName(String name){
        return activityRepository.findByProfileProfileIdAndName(userContext.getAuthenticatedUser().getUserId(), name)
                .map(activityMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Activity with name" + name + "not found"));
    }

    public Page<ActivityResponse> findAll(Pageable pageable) {
        return activityRepository.findAllByProfileProfileId(userContext.getAuthenticatedUser().getUserId(), pageable)
                .map(activityMapper::toResponse);
    }

    public Page<EntityModel<ActivityResponse>> findAllWithLinks(Pageable pageable) {
        Page<Activity> activities = activityRepository.findAllByProfileProfileId(userContext.getAuthenticatedUser().getUserId(), pageable);
        return activities.map(activity -> {
            EntityModel<ActivityResponse> model = EntityModel.of(activityMapper.toResponse(activity));
            if(activity.getPicture() != null) {
                fileAssembler.addLinks(model, activity.getPicture().getFileCode());
            }
            return model;
        });
    }

    //TODO test
    public Integer createActivity(ActivityRequest request) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

        if(activityRepository.findByProfileProfileIdAndName(profile.getProfileId(), request.name()).isPresent()){
            throw new ActionNotAllowed("You already have activity with same name");
        }

        Activity activity = activityMapper.toEntity(request);
        activity.setCreator(userContext.getAuthenticatedUser().getUsername());

        profile.getActivities().add(activity);
        return activityRepository.save(activity).getActivityId();
    }

    //works but improve it
    public ActivityResponse updateActivity(Integer activityId, ActivityRequest request) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getProfile().getProfileId().equals(userContext.getAuthenticatedUser().getUserId())){ //TODO isPrivate not working
            activityMapper.updateToEntity(request, activity);
            activityRepository.save(activity);
        }
        return activityMapper.toResponse(activity);
    }

    //new
    public void addImage(Integer activityId, MultipartFile file){
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
        activity.setPicture(fileService.uploadFile(file));

        activityRepository.save(activity);
    }

    //new
    public ResponseEntity<?> deleteActivityById(Integer activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getProfile().getProfileId().equals(userContext.getAuthenticatedUser().getUserId())){
            if(activity.getPicture() != null) {
                fileService.deleteFile(activity.getPicture().getFilePath());
            }
            for(Achievement a : activity.getAchievements()){
                if(a.getPicture() != null) {
                    fileService.deleteFile(a.getPicture().getFilePath());
                }
            }
            activityRepository.delete(activity);
            return ResponseEntity.ok("Deleted successfully");
        }else
            return ResponseEntity.badRequest().body("You are not creator of this activity");

    }
}