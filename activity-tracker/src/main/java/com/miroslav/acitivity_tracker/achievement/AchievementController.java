package com.miroslav.acitivity_tracker.achievement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("achievement")
public class AchievementController {

    private final AchievementService achievementService;

    //TODO find achievement
    //TODO find all achievements by name
    //TODO create achievement
    //TODO update achievement
    //TODO delete achievement
    //TODO copy achievement to another activity
    //TODO create group -> group entity


    @GetMapping("/{activity-id}/{achievement-id}")
    public ResponseEntity<AchievementResponse> findById(@PathVariable("activity-id") Integer activityId, @PathVariable("achievement-id") Integer achievementId){
        return ResponseEntity.ok(achievementService.findById(activityId, achievementId));
    }

    @GetMapping("/{activity-id}")
    public ResponseEntity<List<AchievementResponse>> findAllById(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(achievementService.findAllById(activityId));
    }

    @PostMapping
    public ResponseEntity<Integer> createAchievement(@RequestBody AchievementRequest request, Authentication user){
        return ResponseEntity.accepted().body(achievementService.createAchievement(request, user));
    }

    @PutMapping("/{activity-id}/{achievement-id}")
    public ResponseEntity<Integer> updateAchievement(@PathVariable("activity-id") Integer activityId,
                                                     @PathVariable("achievement-id") Integer achievementId,
                                                     @RequestBody AchievementRequest request,
                                                     Authentication user){
        return ResponseEntity.accepted().body(achievementService.updateAchievement(activityId, achievementId, request, user));
    }

    @DeleteMapping("/{activity-id}/{achievement-id}")
    public ResponseEntity deleteAchievement(@PathVariable("activity-id") Integer activityId,
                                            @PathVariable("achievement-id") Integer achievementId,
                                            Authentication user){
        return achievementService.deleteAchievement(activityId, achievementId, user);
    }
}
