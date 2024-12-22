package com.miroslav.acitivity_tracker.activity.service;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapper;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final UserContext userContext;
    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public ActivityResponse findById(Integer activityId) {
        return activityRepository.findById(activityId)
                .map(activityMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
    }

    public List<ActivityResponse> findAllByCategory(Category activityCategory) {
        return activityRepository.findAllByCategory(activityCategory)
                .stream()
                .map(activityMapper::toResponse)
                .collect(Collectors.toList());
    }


    //TODO test - need to create addToLibraryFirst
    public ActivityResponse findInUserLibrary(Integer activityId) {
////        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
////                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
//        return activityMapper.toResponse(profile.getActivities()
//                .stream()
//                .filter((Activity a) -> a.getActivityId().equals(activityId))
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException("Activity not found")));
        return activityRepository.findActivityByActivityIdAndProfileProfileId(activityId, userContext.getAuthenticatedUser().getUserId())
                .map(activityMapper::toResponse)
                .orElse(null);

    }

    //TODO test - need to create addToLibraryFirst
    public ActivityResponse findInUserLibraryByName(String name) {
//        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
//                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
//        return activityMapper.toResponse(profile.getActivities()
//                .stream()
//                .filter((Activity a) -> a.getName().equals(name))
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException("Activity not found")));
        return activityRepository.findActivityByNameAndCreatorId(name, (userContext.getAuthenticatedUser().getUserId()))
                .map(activityMapper::toResponse)
                .orElse(null);
    }

    //works
    public ActivityResponse findInMarket(Integer activityId) {
//        Activity activity = activityRepository.findById(activityId)
//                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
//
//        if(!activity.isPrivate())
//            return activityMapper.toResponse(activity);
//        else
//            throw new EntityNotFoundException("Activity is not public");
        return activityRepository.findActivityByActivityIdAndIsPrivate(activityId, false)
                .map(activityMapper::toResponse)
                .orElse(null);
    }

    //works
    public List<ActivityResponse> findInMarketByName(String name) {
//        return activityRepository.findAllByName(name)
//                .stream()
//                .filter((Activity a) -> !a.isPrivate())
//                .map(activityMapper::toResponse)
//                .collect(Collectors.toList());
        return activityRepository.findInMarketByName(name)
                .stream()
                .map(activityMapper::toResponse)
                .collect(Collectors.toList());

    }

    //TODO works, give option to set isPrivate and add to user library in both cases
    public Integer createActivity(ActivityRequest request) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = activityMapper.toEntity(request);
        activity.setCreator(userContext.getAuthenticatedUser().getUsername());
        activity.setCreatorId((userContext.getAuthenticatedUser().getUserId()));
        activity.setOriginal(true);

        //TODO if not private post to market

        profile.getActivities().add(activity);
        return activityRepository.save(activity).getActivityId();
    }

    //works but improve it
    public ActivityResponse copyActivityToUser(Integer activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));


        //TODO check if - already in library

        Activity copiedActivity = new Activity();

        //doesnt work
//        activityMapper.updateToEntity(activity, copiedActivity);

        copiedActivity.setName(activity.getName());
        copiedActivity.setInfo(activity.getInfo());
        copiedActivity.setType(activity.getType());
        copiedActivity.setCategory(activity.getCategory());
        copiedActivity.setPicture(activity.getPicture());
//        copiedActivity.setAchievements(activity.getAchievements()); //TODO fix
        copiedActivity.setOriginal(false);
        copiedActivity.setPrivate(true);
        copiedActivity.setCreator(activity.getCreator());
        copiedActivity.setOriginalActivity(activity.getActivityId());
        copiedActivity.setCreatorId(activity.getCreatorId());

        activity.setDownloads(activity.getDownloads() + 1);
        profile.getActivities().add(copiedActivity);

        activityRepository.save(activity);
        activityRepository.save(copiedActivity);
//        profileRepository.save(profile);

        return activityMapper.toResponse(copiedActivity);
    }

    public ActivityResponse updateActivity(Integer activityId, ActivityRequest request) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getCreatorId().equals(userContext.getAuthenticatedUser().getUserId())){ //TODO isPrivate not working
            activityMapper.updateToEntity(request, activity);
            activityRepository.save(activity);
        }
        return activityMapper.toResponse(activity);
    }

    //TODO test
    public String createGroup(String group) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

        if(!profile.getGroups().containsKey(group))
            profile.getGroups().put(group, new ArrayList<>());

        profileRepository.save(profile);
        return group;
    }


    //TODO test
    public String addToGroup(String group, Integer activityId) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

        Activity activity = profile.getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found in library"));

        if(profile.getGroups().containsKey(group)) {
            profile.getGroups().get(group).add(activity.getActivityId());
        }

        profileRepository.save(profile);
        return group;
    }



    //works
    public ResponseEntity deleteOriginalActivityById(Integer activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getCreatorId().equals(userContext.getAuthenticatedUser().getUserId())){
            activityRepository.delete(activity);
            return ResponseEntity.ok("Deleted successfully");
        }else
            return ResponseEntity.badRequest().body("You are not creator of this activity");

    }

    //works
    public ResponseEntity removeFromUserLibrary(Integer activityId){
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

        profile.getActivities()
                .stream()
                .filter((Activity a) -> a.getActivityId().equals(activityId))
                .findFirst()
                .ifPresent(activityRepository::delete);
        return ResponseEntity.ok("Removed from library");
    }

}