package com.miroslav.acitivity_tracker.achievement.controller;

import com.miroslav.acitivity_tracker.achievement.dto.AchievementRequest;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponse;
import com.miroslav.acitivity_tracker.achievement.model.Type;
import com.miroslav.acitivity_tracker.achievement.service.AchievementService;
import com.miroslav.acitivity_tracker.util.enums.EnumValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("achievement")
public class AchievementController {

    private final AchievementService achievementService;

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

//    @PostMapping("/type")
//    public ResponseEntity<Void> chooseType(@RequestParam("type") @EnumValidator(enumClass = Type.class) String type) throws IOException {
//        RedirectView redirect = new RedirectView();
//        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
//        redirectAttributes.addAttribute(type);
//        HttpHeaders headers = new HttpHeaders();
//        if(type.toLowerCase().equals(Type.AMOUNT.getName())){
//            headers.setLocation(URI.create("/api/v1/achievement/amount"));
//            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
//        }else if(type.toLowerCase().equals(Type.DAILY.getName())){
//            response.sendRedirect("/api/v1/achievement/{activity-id}/daily");
//        }else if(type.toLowerCase().equals(Type.GOAL.getName())){
//            response.sendRedirect("/api/v1/achievement/{activity-id}/goal");
//        }
//        //TODO change
//        System.out.println(type);
//        return null;
//    }

//    @GetMapping("/amount")
//    public ResponseEntity<AchievementResponse> createAmountAchievement(/*@RequestBody AchievementRequest request, @RequestParam("unit") String unit*/){
//        System.out.println("am I here");
//        return ResponseEntity.ok(achievementService.createAmountAchievement(/*request, unit*/));
//    }
//    @PostMapping("/{activity-id}/daily")
//    public ResponseEntity<AchievementResponse> createDailytAchievement(@RequestBody AchievementRequest request, @PathVariable("activity-id") Integer activityId){
//        return ResponseEntity.ok(achievementService.createDailyAchievement(request, activityId));
//    }
//    @PostMapping("/{activity-id}/goal")
//    public ResponseEntity<AchievementResponse> createGoalAchievement(@RequestBody AchievementRequest request, @RequestBody Date deadline, @PathVariable("activity-id") Integer activityId){
//        return ResponseEntity.ok(achievementService.createGoalAchievement(request, deadline, activityId));
//    }

//    @PostMapping("/{activity-id}")
//    public ResponseEntity<Integer> createAchievement(@RequestBody @Valid AchievementRequest request, @PathVariable("activity-id")Integer activityId){
//        return ResponseEntity.accepted().body(achievementService.createAchievement(request, activityId));
//    }

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
