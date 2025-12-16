package com.miroslav.acitivity_tracker.achievement.service;

import com.miroslav.acitivity_tracker.achievement.dto.AchievementRequest;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponse;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponseV2;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponseWType;
import com.miroslav.acitivity_tracker.achievement.mapper.AchievementMapper;
import com.miroslav.acitivity_tracker.achievement.model.*;
import com.miroslav.acitivity_tracker.achievement.repository.*;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.calendar.repository.EventRepository;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.file.assembler.FileAssembler;
import com.miroslav.acitivity_tracker.file.service.FileService;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementTypeService {

    private final UserContext userContext;

    private final ProfileRepository profileRepository;
    private final AchievementRepository achievementRepository;
    private final ActivityRepository activityRepository;

    private final AmountAchievementRepository amountARepository;
    private final DailyAchievementRepository dailyARepository;
    private final DailyAchievementCalendarRepository dailyCalendarRepository;
    private final GoalAchievementRepository goalARepository;
    private final FileService fileService;
    private final FileAssembler<AchievementResponseV2> fileAssembler;

    private final AchievementMapper achievementMapper;

//    public AchievementResponse createDailyAchievement(AchievementRequest request, Integer activityId) {
//        DailyAchievement achievement = achievementMapper.toDailyEntity(request);
//        return saveAchievement(achievement, activityId);
//    }

    private TypeSuperclassRepository getType(Achievement achievement){
        if(achievement.getType() == Type.GOAL){
            return goalARepository;
        }else if(achievement.getType() == Type.DAILY){
            return dailyARepository;
        }else if(achievement.getType() == Type.AMOUNT){
            return amountARepository;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T extends TypeSuperclass> T returnType(Achievement achievement){
        if(achievement.getType() == Type.GOAL){
            return (T)goalARepository.findById(achievement.getAchievementId())
                    .orElseThrow(() -> new EntityNotFoundException("Type not found"));
        }else if(achievement.getType() == Type.DAILY){
            return (T)dailyARepository.findById(achievement.getAchievementId())
                    .orElseThrow(() -> new EntityNotFoundException("Type not found"));
        }else if(achievement.getType() == Type.AMOUNT){
            return (T)amountARepository.findById(achievement.getAchievementId())
                    .orElseThrow(() -> new EntityNotFoundException("Type not found"));
        }
        return null;
    }

    private TypeSuperclassRepository getType(Type type){
        if(type == Type.GOAL){
            return goalARepository;
        }else if(type == Type.DAILY){
            return dailyARepository;
        }else if(type == Type.AMOUNT){
            return amountARepository;
        }
        return null;
    }



    // new
    public AchievementResponseV2 findById(Integer achievementId){
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));
        AchievementResponseV2 responseV2 = new AchievementResponseV2(
                achievement.getAchievementId(),
                achievement.getName(),
                achievement.getInfo(),
                achievement.getType(),
                achievement.getXp()
        );
        try {
            responseV2.setTypeData((TypeSuperclass) getType(achievement.getType()).findById(achievementId)
                    .orElseThrow(() -> new EntityNotFoundException("Type not found")));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
//        if(achievement.getType() == Type.GOAL){
//            GoalAchievement g = goalARepository.findById(achievementId)
//                    .orElseThrow(() -> new EntityNotFoundException("Type not found"));
//            responseV2.setTypeData(g);
//        }else if(achievement.getType() == Type.DAILY){
//            DailyAchievement d = dailyARepository.findById(achievementId)
//                    .orElseThrow(() -> new EntityNotFoundException("Type not found"));
//            responseV2.setTypeData(d);
//        }else if(achievement.getType() == Type.AMOUNT){
//            AmountAchievement a = amountARepository.findById(achievementId)
//                    .orElseThrow(() -> new EntityNotFoundException("Type not found"));
//            responseV2.setTypeData(a);
//        }
        return responseV2;
    }
    public EntityModel<AchievementResponseV2> findByIdWithLinks(Integer achievementId){
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));
        AchievementResponseV2 responseV2 = new AchievementResponseV2(
                achievement.getAchievementId(),
                achievement.getName(),
                achievement.getInfo(),
                achievement.getType(),
                achievement.getXp()
        );
        try {
            responseV2.setTypeData((TypeSuperclass) getType(achievement.getType()).findById(achievementId)
                    .orElseThrow(() -> new EntityNotFoundException("Type not found")));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        EntityModel<AchievementResponseV2> model = EntityModel.of(responseV2);
        if(achievement.getPicture() != null)
            fileAssembler.addLinks(model, achievement.getPicture().getFileCode());
        return model;
    }

    public AchievementResponseV2 getFromActivity(Integer activityId, Integer achievementId){
        //find achievement
        Achievement achievement = achievementRepository.findPublicById(achievementId, activityId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));
        //find achievement type
        //fuck shit up
        //map to dtos
        AchievementResponseV2 responseV2 = new AchievementResponseV2(
                achievement.getAchievementId(),
                achievement.getName(),
                achievement.getInfo(),
                achievement.getType(),
                achievement.getXp()
        );
//        AchievementResponseWType response = achievementMapper.toResponseWType(achievement);
        if(achievement.getType() == Type.GOAL){
            GoalAchievement g = goalARepository.findById(achievementId)
                            .orElseThrow(() -> new EntityNotFoundException("Type not found"));
            responseV2.setTypeData(g);
        }else if(achievement.getType() == Type.DAILY){
            DailyAchievement d = dailyARepository.findById(achievementId)
                    .orElseThrow(() -> new EntityNotFoundException("Type not found"));
            responseV2.setTypeData(d);
        }else if(achievement.getType() == Type.AMOUNT){
            AmountAchievement a = amountARepository.findById(achievementId)
                    .orElseThrow(() -> new EntityNotFoundException("Type not found"));
//                    (AmountAchievement) getType(achievement).findById(achievementId)
//                    .orElse(null);
            responseV2.setTypeData(a);
        }
        //return
        return responseV2;
    }

    //TODO this doesn't seem right
    public List<AchievementResponseV2> getAllFromActivity(Integer activityId){
        //find by activity
        List<Achievement> achievements = achievementRepository.findAllPublicById(activityId);
        List<AchievementResponseV2> response = new ArrayList<>();
        // check user
        //map to AchievementResponse
        for(Achievement a : achievements){
            AchievementResponseV2 res = new AchievementResponseV2(
                    a.getAchievementId(),
                    a.getName(),
                    a.getInfo(),
                    a.getType(),
                    a.getXp()
                    );
            res.setTypeData((TypeSuperclass) getType(a).findById(a.getAchievementId()).orElse(null));
            response.add(res);
        }
        return response;
    }

    //new
    public Page<AchievementResponseV2> getAllFromActivityPage(Integer activityId, Pageable pageable){
        Page<Achievement> achievements = achievementRepository.findAllByActivityActivityId(activityId, pageable);
        return achievements.map(
                achievement -> {
                    AchievementResponseV2 res = new AchievementResponseV2(
                            achievement.getAchievementId(),
                            achievement.getName(),
                            achievement.getInfo(),
                            achievement.getType(),
                            achievement.getXp()
                    );
                    res.setTypeData((TypeSuperclass) getType(achievement).findById(achievement.getAchievementId()).orElse(null));
                    return res;
                }
        );
    }

    public Integer createGoalAchievement(AchievementRequest request, Integer activityId) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getAchievements().stream().anyMatch(a -> a.getName().equals(request.name()))){
            throw new ActionNotAllowed("This activity already has achievement with same name");
        }

        //TODO test how to persist/ change cascades in entities

        Achievement achievement = achievementMapper.toEntity(request);
        activity.getAchievements().add(achievement);
        achievement.setType(Type.GOAL);

        GoalAchievement goalAchievement = GoalAchievement.builder()
                .typeAchievementId(achievementRepository.save(achievement).getAchievementId())
                .achievement(achievement)
                .deadline(request.deadline())
                .setXPGain(request.setXPGain())
                .build();

        return goalARepository.save(goalAchievement).getTypeAchievementId();
    }
    public Integer createDailyAchievement(AchievementRequest request, Integer activityId) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getAchievements().stream().anyMatch(a -> a.getName().equals(request.name()))){
            throw new ActionNotAllowed("This activity already has achievement with same name");
        }

        //TODO test how to persist/ change cascades in entities

        Achievement achievement = achievementMapper.toEntity(request);
        activity.getAchievements().add(achievement);
        achievement.setType(Type.DAILY);

        DailyAchievement dailyAchievement = DailyAchievement.builder()
                .typeAchievementId(achievementRepository.save(achievement).getAchievementId())
                .achievement(achievement)
                .setXPGain(request.setXPGain())
                .build();
        //TODO DailyAchievementCalendar
        var dailyToSave = dailyARepository.save(dailyAchievement);
        DailyAchievementCalendar cal = DailyAchievementCalendar.builder()
                .day(LocalDate.now())
                .dailyAchievement(dailyToSave)
                .build();

        dailyCalendarRepository.save(cal);

//        achievement.setTypeSuperclass(dailyARepository.save(dailyAchievement).getTypeAchievementId());

//        profileRepository.save(profile);
//        activityRepository.save(activity);
        return dailyToSave.getTypeAchievementId();
    }
    public Integer createAmountAchievement(AchievementRequest request, Integer activityId) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getAchievements().stream().anyMatch(a -> a.getName().equals(request.name()))){
            throw new ActionNotAllowed("This activity already has achievement with same name");
        }

        Achievement achievement = achievementMapper.toEntity(request);
        activity.getAchievements().add(achievement);
        achievement.setType(Type.AMOUNT);

        AmountAchievement amountAchievement = AmountAchievement.builder()
                .typeAchievementId(achievementRepository.save(achievement).getAchievementId())
                .achievement(achievement)
                .setXPGain(request.setXPGain())
                .unit(request.unit())
                .build();

//        achievement.setTypeSuperclass(amountARepository.save(amountAchievement).getTypeAchievementId());

//        profileRepository.save(profile);
//        activityRepository.save(activity);
        return amountARepository.save(amountAchievement).getTypeAchievementId();
    }

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
//        return achievementRepository.save(achievement).getAchievementId();
//    }

    //new
    public void addImage(Integer achievementId, MultipartFile file){
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));
        achievement.setPicture(fileService.uploadFile(file));

        achievementRepository.save(achievement);
    }

    //new
    public void deleteAchievement(Integer achievementId){
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));

        if(achievement.getType() == Type.DAILY){
            var a = dailyCalendarRepository.findByDailyAchievement_TypeAchievementId(achievementId)
                    .orElseThrow(() -> new EntityNotFoundException("Daily achievement not found"));
            dailyCalendarRepository.delete(a);
        }

        getType(achievement).deleteById(achievementId);


        if(!achievement.getActivity().getProfile().getProfileId().equals(profile.getProfileId())){
            throw new ActionNotAllowed("You are not allowed to change this template");
        }

        if(achievement.getPicture() != null) {
            fileService.deleteFile(achievement.getPicture().getFilePath());
        }

//        if(achievement.getType() == Type.GOAL){
//            goalARepository.deleteById(achievement.getTypeSuperclass());
//        }else if(achievement.getType() == Type.DAILY){
//            dailyARepository.deleteById(achievement.getTypeSuperclass());
//        }else if(achievement.getType() == Type.AMOUNT){
//            amountARepository.deleteById(achievement.getTypeSuperclass());
//        }else{
//            //TODO probably not needed, but keep it for tests
//            throw new ActionNotAllowed("Type not set properly unable to delete achievement");
//        }
        achievementRepository.delete(achievement);
    }
}
