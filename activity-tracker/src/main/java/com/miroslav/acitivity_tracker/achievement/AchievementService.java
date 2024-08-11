package com.miroslav.acitivity_tracker.achievement;

import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.activity.ActivityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {

//    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final ActivityRepository activityRepository;

    public AchievementResponse findById(Integer activityId, Integer achievementId) {
//        achievementRepository.findById()
        return null;
    }

    public AchievementResponse findPublicById(Integer activityId, Integer achievementId){
        return achievementMapper.toResponse(
                activityRepository.findById(activityId)
                        .orElseThrow(() -> new EntityNotFoundException("Activity not found"))
                        .getAchievements()
                        .stream()
                        .filter((Achievement a) -> a.getAchievementId().equals(achievementId))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Achievement not found"))
        );
    }

    public List<AchievementResponse> findAllById(Integer activityId) {
        return null;
    }

    public Integer createAchievement(AchievementRequest request, Authentication user) {
        return null;
    }

    public Integer updateAchievement(Integer activityId, Integer achievementId, AchievementRequest request, Authentication user) {
        return null;
    }

    public ResponseEntity deleteAchievement(Integer activityId, Integer achievementId, Authentication user) {
        return null;
    }
}
