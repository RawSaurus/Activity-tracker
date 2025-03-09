package com.miroslav.acitivity_tracker.achievement.service;

import com.miroslav.acitivity_tracker.achievement.dto.AchievementRequest;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponse;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponseWType;
import com.miroslav.acitivity_tracker.achievement.mapper.AchievementMapper;
import com.miroslav.acitivity_tracker.achievement.model.*;
import com.miroslav.acitivity_tracker.achievement.repository.*;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementTypeService {

    //TODO move finished field straight to achievement

    private final UserContext userContext;
    private final ProfileRepository profileRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final AmountAchievementRepository amountARepository;
    private final DailyAchievementRepository dailyARepository;
    private final DailyAchievementCalendarRepository dailyCalendar;
    private final GoalAchievementRepository goalARepository;

//    public AchievementResponse createDailyAchievement(AchievementRequest request, Integer activityId) {
//        DailyAchievement achievement = achievementMapper.toDailyEntity(request);
//        return saveAchievement(achievement, activityId);
//    }

    //TODO this doesn't seem right
    public List<AchievementResponseWType> getAllFromActivity(Integer activityId){
        //find by activity
        List<Achievement> achievements = achievementRepository.findAllPublicById(activityId);
        List<AchievementResponseWType> response = new ArrayList<>();
        // check user
        //map to AchievementResponse
        for(Achievement a : achievements){
            AchievementResponseWType res = achievementMapper.toResponseWType(a);
        }
            //get type, find in repo and transform to Response and add it to achievement response
        //add to list
        return null;
    }

    public Integer createGoalAchievement(AchievementRequest request, Integer activityId, Date deadline, int setXpGain) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = profile.getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getAchievements().stream().anyMatch(a -> a.getName().equals(request.name()))){
            throw new ActionNotAllowed("This activity already has achievement with same name");
        }

        //TODO test how to persist/ change cascades in entities

        Achievement achievement = achievementMapper.toEntity(request);
        activity.getAchievements().add(achievement);

        GoalAchievement goalAchievement = GoalAchievement.builder()
                .deadline(deadline)
                .setXPGain(setXpGain)
                .build();

        achievement.setTypeSuperclass(goalARepository.save(goalAchievement).getTypeAchievementId());
        achievement.setType(Type.GOAL);

//        profileRepository.save(profile);
//        activityRepository.save(activity);
        return achievementRepository.save(achievement).getAchievementId();
    }
    public Integer createDailyAchievement(AchievementRequest request, Integer activityId, int setXpGain) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = profile.getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getAchievements().stream().anyMatch(a -> a.getName().equals(request.name()))){
            throw new ActionNotAllowed("This activity already has achievement with same name");
        }

        //TODO test how to persist/ change cascades in entities

        Achievement achievement = achievementMapper.toEntity(request);
        activity.getAchievements().add(achievement);

        DailyAchievement dailyAchievement = DailyAchievement.builder()
                .setXPGain(setXpGain)
                .build();
        //TODO DailyAchievementCalendar

        achievement.setTypeSuperclass(dailyARepository.save(dailyAchievement).getTypeAchievementId());
        achievement.setType(Type.DAILY);

//        profileRepository.save(profile);
//        activityRepository.save(activity);
        return achievementRepository.save(achievement).getAchievementId();
    }
    public Integer createAmountAchievement(AchievementRequest request, Integer activityId, int setXpGain, String unit) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = profile.getActivities()
                .stream()
                .filter(a -> a.getActivityId().equals(activityId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getAchievements().stream().anyMatch(a -> a.getName().equals(request.name()))){
            throw new ActionNotAllowed("This activity already has achievement with same name");
        }

        //TODO test how to persist/ change cascades in entities

        Achievement achievement = achievementMapper.toEntity(request);
        activity.getAchievements().add(achievement);

        AmountAchievement amountAchievement = AmountAchievement.builder()
                .setXPGain(setXpGain)
                .unit(unit)
                .build();

        achievement.setTypeSuperclass(amountARepository.save(amountAchievement).getTypeAchievementId());
        achievement.setType(Type.AMOUNT);

//        profileRepository.save(profile);
//        activityRepository.save(activity);
        return achievementRepository.save(achievement).getAchievementId();
    }

//    public AchievementResponse createGoalAchievement(AchievementRequest request, Integer activityId) {
//        GoalAchievement achievement = achievementMapper.toGoalEntity(request);
//        return saveAchievement(achievement, activityId);
//    }

//    public AchievementResponse createAmountAchievement(AchievementRequest request, Integer activityId) {
//        AmountAchievement achievement = achievementMapper.toAmountEntity(request);
//        return saveAchievement(achievement, activityId);
//    }

////    private AchievementResponse saveAchievement(Achievement achievement, Integer activityId) {
//////        // Logic to save the achievement
////        achievementRepository.save(achievement);
////        return achievementMapper.toResponse(achievement);
//    }
//
//    public Integer updateAchievement(Integer activityId, Integer achievementId, AchievementRequest request) {
//        Achievement achievement = achievementRepository.findFromProfile(
//                        achievementId,
//                        activityId,
//                        (userContext.getAuthenticatedUser().getUserId())
//                )
//                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));
//
//        achievementMapper.updateToEntity(request, achievement);
//
//
//        if(achievement.getType() == Type.GOAL){
//            goalARepository.findById(achievement.getTypeSuperclass());
//        }else if(achievement.getType() == Type.DAILY){
//            dailyARepository.findById(achievement.getTypeSuperclass());
//        }else if(achievement.getType() == Type.AMOUNT){
//            amountARepository.findById(achievement.getTypeSuperclass());
//        }else{
//            //TODO probably not needed, but keep it for tests
//            throw new ActionNotAllowed("Type not set properly unable to delete achievement");
//        }
//
//        return achievementRepository.save(achievement).getAchievementId();
//    }
//
//    public AchievementResponse updateAchievement(Integer achievementId, AchievementRequest request) {
//        Achievement achievement = achievementRepository.findById(achievementId)
//                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));
//        achievementMapper.updateToEntity(request, achievement);
//        achievementRepository.save(achievement);
//        return achievementMapper.toResponse(achievement);
//    }

    public void deleteAchievement(Integer achievementId){
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("achievement not found"));

        if(achievement.getType() == Type.GOAL){
            goalARepository.deleteById(achievement.getTypeSuperclass());
        }else if(achievement.getType() == Type.DAILY){
            dailyARepository.deleteById(achievement.getTypeSuperclass());
        }else if(achievement.getType() == Type.AMOUNT){
            amountARepository.deleteById(achievement.getTypeSuperclass());
        }else{
            //TODO probably not needed, but keep it for tests
            throw new ActionNotAllowed("Type not set properly unable to delete achievement");
        }
        achievementRepository.delete(achievement);
    }

//    public List<AchievementResponse> findAllAchievementsByActivityId(Integer activityId) {
//        return achievementRepository.findAllByActivityId(activityId)
//                .stream()
//                .map(achievementMapper::toResponse)
//                .collect(Collectors.toList());
//    }
}
