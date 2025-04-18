package com.miroslav.acitivity_tracker.achievement.service;

import com.miroslav.acitivity_tracker.achievement.dto.AchievementRequest;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponse;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponseWType;
import com.miroslav.acitivity_tracker.achievement.mapper.AchievementMapper;
import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.achievement.repository.AchievementRepository;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.calendar.model.Event;
import com.miroslav.acitivity_tracker.calendar.model.EventType;
import com.miroslav.acitivity_tracker.calendar.repository.EventRepository;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.file.assembler.FileAssembler;
import com.miroslav.acitivity_tracker.file.service.FileService;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final UserContext userContext;
    private final ProfileRepository profileRepository;
    private final EventRepository eventRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final ActivityRepository activityRepository;
    private final FileService fileService;
    private final FileAssembler<AchievementResponseWType> fileAssembler;



    public AchievementResponse findById(Integer activityId, Integer achievementId) {
        return achievementMapper.toResponse(achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found")));
    }

    //TODO test
    public AchievementResponse findPublicById(Integer activityId, Integer achievementId){
        return achievementRepository.findPublicById(achievementId, activityId)
                .map(achievementMapper::toResponse)
                .orElse(null);
    }

    //TODO test
    public List<AchievementResponse> findAllPublicById(Integer activityId) {
        return achievementRepository.findAllPublicById(activityId)
                .stream()
                .map(achievementMapper::toResponse)
                .collect(Collectors.toList());
    }

//    public EntityModel<AchievementResponseWType> findByIdWithLinks(Integer activityId, Integer achievementId) {
//        Achievement achievement = achievementRepository.findById(achievementId)
//                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));
//        EntityModel<AchievementResponseWType> model = EntityModel.of(achievementMapper.toResponse(achievement));
//        fileAssembler.addLinks(model, achievement.getPicture().getFileCode());
//        return model;
//    }

//    public AchievementResponse createTypeAchievement(AchievementRequest request){
//        Achievement achievement = achievementMapper.toEntity(request);
//        if(achievement.getType().equals(Type.DAILY)) {
//          something
//        }
//    }

    public Integer createAchievement(AchievementRequest request, Integer activityId) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if(activity.getAchievements().stream().anyMatch(a -> a.getName().equals(request.name()))){
            throw new ActionNotAllowed("This activity already has achievement with same name");
        }

        Achievement achievement = achievementMapper.toEntity(request);
        activity.getAchievements().add(achievement);

        return achievementRepository.save(achievement).getAchievementId();
    }

    //TODO create checks
    public Integer updateAchievement(Integer activityId, Integer achievementId, AchievementRequest request) {
        Achievement achievement = achievementRepository.findFromProfile(
                achievementId,
                activityId,
                (userContext.getAuthenticatedUser().getUserId())
                )
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));

        achievementMapper.updateToEntity(request, achievement);

        return achievementRepository.save(achievement).getAchievementId();
    }

    // new
    // TODO in query check first if finished == true already
    public void markFinished(Integer achievementId){
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found"));

        Event event = Event.builder()
                .name(achievement.getName() + " finished")
                .type(EventType.ACHIEVEMENT_DONE)
                .linkId(achievementId)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .profile(profile)
                .build();
        eventRepository.save(event);


        achievementRepository.updateFinished(achievementId);
    }


    //TODO works/ add checks
    //TODO not needed since AchievementTypeService, if kept change to just delete by repo method
    public ResponseEntity deleteAchievement(Integer activityId, Integer achievementId) {

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
