package com.miroslav.acitivity_tracker.achievement.controller;

import com.miroslav.acitivity_tracker.achievement.dto.AchievementRequest;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponse;
import com.miroslav.acitivity_tracker.achievement.dto.AchievementResponseV2;
import com.miroslav.acitivity_tracker.achievement.model.Type;
import com.miroslav.acitivity_tracker.achievement.service.AchievementService;
import com.miroslav.acitivity_tracker.achievement.service.AchievementTypeService;
import com.miroslav.acitivity_tracker.util.enums.EnumValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private final AchievementTypeService achievementTypeService;

    //TODO add authorization

    @GetMapping("/{activity-id}/{achievement-id}")
    public ResponseEntity<AchievementResponse> findById(@PathVariable("activity-id") Integer activityId, @PathVariable("achievement-id") Integer achievementId){
        return ResponseEntity.ok(achievementService.findById(activityId, achievementId));
    }

    // new
    @GetMapping("/find-one/{achievement-id}")
    public ResponseEntity<AchievementResponseV2> findById(@PathVariable("achievement-id")Integer achievementId){
        return ResponseEntity.ok(achievementTypeService.findById(achievementId));
    }

//    @GetMapping("/market/{activity-id}/{achievement-id}")
//    public ResponseEntity<AchievementResponse> findPublicById(@PathVariable("activity-id") Integer activityId, @PathVariable("achievement-id") Integer achievementId){
//        return ResponseEntity.ok(achievementService.findPublicById(activityId, achievementId));
//    }
//
//    @GetMapping("/{activity-id}")
//    public ResponseEntity<List<AchievementResponse>> findAllById(@PathVariable("activity-id") Integer activityId){
//        return ResponseEntity.ok(achievementService.findAllPublicById(activityId));
//    }

    @GetMapping("/get-from-activity/{activity-id}/{achievement-id}")
    public ResponseEntity<AchievementResponseV2> getFromActivity(@PathVariable("activity-id") Integer activityId, @PathVariable("achievement-id") Integer achievementId){
        return ResponseEntity.ok(achievementTypeService.getFromActivity(activityId, achievementId));
    }

    @GetMapping("/get-all-from-activity/{activity-id}")
    public ResponseEntity<List<AchievementResponseV2>> getAllFromActivity(@PathVariable("activity-id")Integer activityId){
        return ResponseEntity.ok(achievementTypeService.getAllFromActivity(activityId));
    }

    //new
    @GetMapping("all/{activity-id}")
    public ResponseEntity<Page<AchievementResponseV2>> findAll(
            @PathVariable("activity-id") Integer activityId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "name") String sortBy,
            @RequestParam(value = "sort-direction", defaultValue = "asc") String sortDirection){
        return ResponseEntity.ok(achievementTypeService.getAllFromActivityPage(activityId, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy))));
    }

    @GetMapping("/links/{achievement-id}")
    public ResponseEntity<EntityModel<AchievementResponseV2>> findByIdWithLinks(@PathVariable("achievement-id") Integer achievementId){
        return ResponseEntity.ok(achievementTypeService.findByIdWithLinks(achievementId));
    }

    @PostMapping("/goal-achievement/{activity-id}")
    public ResponseEntity<Integer> createGoalAchievement(@RequestBody AchievementRequest request,
                                                         @PathVariable("activity-id")Integer activityId,
                                                         @RequestParam("deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date deadline,
                                                         @RequestParam("setXpGain") int setXpGain
                                                         ){
        return ResponseEntity.ok(achievementTypeService.createGoalAchievement(request, activityId, deadline, setXpGain));
    }
    @PostMapping("/daily-achievement/{activity-id}")
    public ResponseEntity<Integer> createDailyAchievement(@RequestBody AchievementRequest request,
                                                         @PathVariable("activity-id")Integer activityId,
                                                         @RequestParam int setXpGain
    ){
        return ResponseEntity.ok(achievementTypeService.createDailyAchievement(request, activityId, setXpGain));
    }
    @PostMapping("/amount-achievement/{activity-id}")
    public ResponseEntity<Integer> createAmountAchievement(@RequestBody AchievementRequest request,
                                                        @PathVariable("activity-id")Integer activityId,
                                                        @RequestParam int setXpGain,
                                                        @RequestParam String unit
    ){
        return ResponseEntity.ok(achievementTypeService.createAmountAchievement(request, activityId, setXpGain, unit));
    }

    @DeleteMapping("/delete-achievement/{achievement-id}")
    public ResponseEntity deleteAchievement(@PathVariable("achievement-id")Integer achievementId){
        achievementTypeService.deleteAchievement(achievementId);
        return ResponseEntity.status(HttpStatus.OK).build();
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

    @PatchMapping("/{achievement-id}")
    public ResponseEntity markFinished(@PathVariable("achievement-id")Integer achievementId){
        achievementService.markFinished(achievementId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/image/{achievement-id}")
    public ResponseEntity<?> addImage(@PathVariable("achievement-id") Integer achievementId, @RequestParam MultipartFile file){
        achievementTypeService.addImage(achievementId, file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{activity-id}/{achievement-id}")
    public ResponseEntity deleteAchievement(@PathVariable("activity-id") Integer activityId,
                                            @PathVariable("achievement-id") Integer achievementId){
        return achievementService.deleteAchievement(activityId, achievementId);
    }
}
