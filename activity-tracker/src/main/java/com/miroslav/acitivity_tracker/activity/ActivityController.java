package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("activity")
public class ActivityController {

    private final ActivityService activityService;


    //TODO find activity
    //TODO find all activities by name
    //TODO find all activities by category
    //TODO create activity
    //TODO update activity
    //TODO delete activity
    //TODO copy activity to user
    //TODO create group -> group entity
    //TODO post activity to market

    @GetMapping("/{activity-id}")
    public ResponseEntity<ActivityResponse> findById(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findById(activityId));
    }

    @PostMapping
    public ResponseEntity<Integer> createActivity(@RequestBody ActivityRequest request, Authentication user){
        return ResponseEntity.ok(activityService.createActivity(request, user));
    }

    @PutMapping("/{activity-id}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable("activity-id") Integer activityId,
                                                           Authentication user){
        return ResponseEntity.accepted().body(activityService.updateActivity(activityId, user));
    }

    @DeleteMapping("/{activity-id}")
    public ResponseEntity deleteActivityById(@PathVariable("activity-id") Integer activityId){
        return activityService.deleteActivityById(activityId);
    }
}
