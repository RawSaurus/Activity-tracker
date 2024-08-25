package com.miroslav.acitivity_tracker.achievement;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.activity.ActivityRepository;
import com.miroslav.acitivity_tracker.activity.ActivityService;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final ProfileRepository profileRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final ActivityRepository activityRepository;



    //-------------------------------------------------------------------------------------------------------------------------------------------------//
    //works
    public AchievementResponse findById(Integer activityId, Integer achievementId) {
        return achievementMapper.toResponse(achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found")));
    }

    //works
    public AchievementResponse findPublicById(Integer activityId, Integer achievementId){
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(!activity.isPrivate()){
            return achievementMapper.toResponse(
                    activity.getAchievements()
                            .stream()
                            .filter(a -> a.getAchievementId().equals(achievementId))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Achievement not found"))
            );
        } else//TODO change ex
            throw new RuntimeException("Activity you are looking for is not public");
    }

    //works
    public List<AchievementResponse> findAllById(Integer activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(!activity.isPrivate()){
            return activity.getAchievements()
                            .stream()
                            .map(achievementMapper::toResponse)
                            .collect(Collectors.toList());
        } else //TODO change ex
            throw new RuntimeException("Activity you are looking for is not public");
    }


    //TODO findAllFromUserActivity
    //TODO findFromUserActivity
    //-------------------------------------------------------------------------------------------------------------------------------------------------//

    //TODO works/ create checks if activity public if creator == user, or if private if activity is in user library
    public Integer createAchievement(AchievementRequest request, Integer activityId, Authentication user) {
        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = profile.getActivities()
                        .stream()
                        .filter(a -> a.getActivityId().equals(activityId))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        //TODO test how to persist/ change cascades in entities

        Achievement achievement = achievementMapper.toEntity(request);
        activity.getAchievements().add(achievement);

//        profileRepository.save(profile);
//        activityRepository.save(activity);
        return achievementRepository.save(achievement).getAchievementId();
    }

    //works create checks
    public Integer updateAchievement(Integer activityId, Integer achievementId, AchievementRequest request, Authentication user) {
        Profile profile = profileRepository.findById(((User) user.getPrincipal()).getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Achievement achievement = profile.getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"))
                .getAchievements()
                .stream()
                .filter(a -> a.getAchievementId().equals(achievementId))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));

        achievementMapper.updateToEntity(request, achievement);

        return achievementRepository.save(achievement).getAchievementId();
    }

    //TODO works/ add checks
    public ResponseEntity deleteAchievement(Integer activityId, Integer achievementId, Authentication user) {

        Activity activity = activityRepository.findById(activityId)
                        .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
        for (Achievement a: activity.getAchievements()) {
           if(a.getAchievementId().equals(achievementId)) {
               activity.getAchievements().remove(a);
               break;
           }
        }
        activityRepository.save(activity);
        achievementRepository.deleteById(achievementId);

        return ResponseEntity.ok("Achievement deleted");
    }
}
