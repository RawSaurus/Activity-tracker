package com.miroslav.acitivity_tracker.achievement.controller;

import com.miroslav.acitivity_tracker.achievement.dto.AchievementRequest;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponse;
import com.miroslav.acitivity_tracker.achievement.service.AchievementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("achievement")
public class AchievementController {

    private final AchievementService achievementService;

    //TODO find all achievements by name
    //TODO copy achievement to another activity
    //TODO create group -> group entity
    //TODO add authorization


    @GetMapping("/{activity-id}/{achievement-id}")
    public ResponseEntity<AchievementResponse> findById(@PathVariable("activity-id") Integer activityId, @PathVariable("achievement-id") Integer achievementId){
        return ResponseEntity.ok(achievementService.findById(activityId, achievementId));
    }

    @GetMapping("/market/{activity-id}/{achievement-id}")
    public ResponseEntity<AchievementResponse> findPublicById(@PathVariable("activity-id") Integer activityId, @PathVariable("achievement-id") Integer achievementId){
        return ResponseEntity.ok(achievementService.findPublicById(activityId, achievementId));
    }

    @GetMapping("/{activity-id}")
    public ResponseEntity<List<AchievementResponse>> findAllById(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(achievementService.findAllPublicById(activityId));
    }

    @PostMapping("/{activity-id}")
    public ResponseEntity<Integer> createAchievement(@RequestBody @Valid AchievementRequest request, @PathVariable("activity-id")Integer activityId){
        return ResponseEntity.accepted().body(achievementService.createAchievement(request, activityId));
    }

    @PutMapping("/{activity-id}/{achievement-id}")
    public ResponseEntity<Integer> updateAchievement(@PathVariable("activity-id") Integer activityId,
                                                     @PathVariable("achievement-id") Integer achievementId,
                                                     @RequestBody @Valid AchievementRequest request){
        return ResponseEntity.accepted().body(achievementService.updateAchievement(activityId, achievementId, request));
    }

    @DeleteMapping("/{activity-id}/{achievement-id}")
    public ResponseEntity deleteAchievement(@PathVariable("activity-id") Integer activityId,
                                            @PathVariable("achievement-id") Integer achievementId){
        return achievementService.deleteAchievement(activityId, achievementId);
    }
}
