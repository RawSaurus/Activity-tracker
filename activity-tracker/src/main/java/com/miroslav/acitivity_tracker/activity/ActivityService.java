package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.UserRepository;
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

    public Integer createActivity(ActivityRequest request, Authentication user) {
        Activity activity = activityMapper.toEntity(request);
        activity.setCreator((Profile) user.getPrincipal());
        activity.setOriginal(true);

        //TODO if not private post to market

        return activityRepository.save(activity).getActivityId();
    }

    public ActivityResponse updateActivity(Integer activityId, Authentication user) {
        return null;
    }

    public ActivityResponse copyActivityToUser(Integer activityId, Authentication user) {
        return null;
    }

    public ResponseEntity deleteActivityById(Integer activityId, Authentication user) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getCreator().equals((User)user.getPrincipal())){
            activityRepository.delete(activity);
        }
        return ResponseEntity.ok("Deleted successfully");
    }
}