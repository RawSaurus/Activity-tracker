package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public ActivityResponse findById(Integer activityId) {
        return activityRepository.findById(activityId)
                .map(activityMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
    }

    public List<ActivityResponse> findAllByCategory(String activityCategory) {
        return activityRepository.findAllByCategory(activityCategory)
                .orElseThrow(() -> new EntityNotFoundException("No activities found in this category"))
                .stream()
                .map(activityMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse findByName(String activityName) {
        return activityRepository.findByName(activityName)
                .map(activityMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("No activities found with this name"));
    }

    //TODO test - need to create addToLibraryFirst
    public ActivityResponse findInUserLibrary(Integer activityId, Authentication user) {
        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        return activityMapper.toResponse(profile.getActivities()
                .stream()
                .filter((Activity a) -> a.getActivityId().equals(activityId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found")));
    }

    //TODO test - need to create addToLibraryFirst
    public ActivityResponse findInUserLibraryByName(String name, Authentication user) {
        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        return activityMapper.toResponse(profile.getActivities()
                .stream()
                .filter((Activity a) -> a.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found")));
    }

    //TODO works, but make find all
    public ActivityResponse findInMarket(Integer activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(!activity.isPrivate())
            return activityMapper.toResponse(activity);
        else
            throw new EntityNotFoundException("Activity is not public");
    }

    //TODO works, but make find all
    public ActivityResponse findInMarketByName(String name) {
        Activity activity = activityRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(!activity.isPrivate())
            return activityMapper.toResponse(activity);
        else
            throw new EntityNotFoundException("Activity is not public");
    }

    //TODO test
    public Integer createActivity(ActivityRequest request, Authentication user) {
        Activity activity = activityMapper.toEntity(request);
        activity.setCreator(((User) user.getPrincipal()).getUsername());
        activity.setCreatorId((((User) user.getPrincipal()).getUserId()));
        activity.setOriginal(true);

        //TODO if not private post to market

        return activityRepository.save(activity).getActivityId();
    }

    public ActivityResponse updateActivity(Integer activityId, ActivityRequest request, Authentication user) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getCreatorId().equals(((User)user.getPrincipal()).getUserId())){ //TODO not working
            activityMapper.updateToEntity(request, activity);
            activityRepository.save(activity);
        }
        return activityMapper.toResponse(activity);
    }

    public String createGroup(String group, Authentication user) {
        return null;
    }

    public String addToGroup(String group, Authentication user) {
        return null;
    }


    public ActivityResponse copyActivityToUser(Integer activityId, Authentication user) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));


        //TODO check if - already in library

        Activity copiedActivity = new Activity();
        copiedActivity.setName(activity.getName());
        copiedActivity.setInfo(activity.getInfo());
        copiedActivity.setType(activity.getType());
        copiedActivity.setCategory(activity.getCategory());
        copiedActivity.setPicture(activity.getPicture());
        copiedActivity.setOriginal(false);
        copiedActivity.setPrivate(true);
        copiedActivity.setCreator(activity.getCreator());
        copiedActivity.setOriginalActivity(activity);
        copiedActivity.setCreatorId(((User)user.getPrincipal()).getUserId());

        activity.setDownloads(activity.getDownloads() + 1);
        profile.getActivities().add(copiedActivity);

        activityRepository.save(activity);
        activityRepository.save(copiedActivity);
        profileRepository.save(profile);
        System.out.println(profile.getActivities());

        return activityMapper.toResponse(copiedActivity);
    }

    //TODO test
    public ResponseEntity deleteOriginalActivityById(Integer activityId, Authentication user) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getCreatorId().equals(((User)user.getPrincipal()).getUserId())){ //TODO not working
            activityRepository.delete(activity);
        }
        return ResponseEntity.ok("Deleted successfully");
    }

    //TODO test
    public ResponseEntity removeFromUserLibrary(Integer activityId, Authentication user){
        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

        profile.getActivities()
                .stream()
                .filter((Activity a) -> a.getActivityId().equals(activityId))
                .findFirst()
                .ifPresent(activityRepository::delete);
        return ResponseEntity.ok("Removed from library");
    }

}