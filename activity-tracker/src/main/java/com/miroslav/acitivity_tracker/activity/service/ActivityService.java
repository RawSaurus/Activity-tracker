package com.miroslav.acitivity_tracker.activity.service;

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
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final UserContext userContext;
    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    //TODO create unit tests
    public ActivityResponse findById(Integer activityId) {
        return activityRepository.findById(activityId)
                .map(activityMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
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

    public ResponseEntity deleteActivityById(Integer activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getProfile().getProfileId().equals(userContext.getAuthenticatedUser().getUserId())){
            activityRepository.delete(activity);
            return ResponseEntity.ok("Deleted successfully");
        }else
            return ResponseEntity.badRequest().body("You are not creator of this activity");

    }
}