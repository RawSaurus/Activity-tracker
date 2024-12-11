package com.miroslav.acitivity_tracker.activity.controller;

import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("activity")
public class ActivityController {

    private final ActivityService activityService;


    @GetMapping("/{activity-id}")
    public ResponseEntity<ActivityResponse> findById(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findById(activityId));
    }

    @GetMapping("/library/{activity-id}")//TODO
    public ResponseEntity<ActivityResponse> findInUserLibrary(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findInUserLibrary(activityId));
    }
    @GetMapping("/library/name/{name}")//TODO
    public ResponseEntity<ActivityResponse> findInUserLibraryByName(@PathVariable("name") String name, Authentication user){
        return ResponseEntity.ok(activityService.findInUserLibraryByName(name, user));
    }

    @GetMapping("/market/{activity-id}")
    public ResponseEntity<ActivityResponse> findInMarket(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findInMarket(activityId));
    }

    @GetMapping("/market/name/{name}")
    public ResponseEntity<List<ActivityResponse>> findInMarketByName(@PathVariable("name") String name){
        return ResponseEntity.ok(activityService.findInMarketByName(name));
    }

    @GetMapping("/category/{activity-category}")
    public ResponseEntity<List<ActivityResponse>> findAllByCategory(@PathVariable("activity-category") String activityCategory){
        return ResponseEntity.ok(activityService.findAllByCategory(activityCategory));
    }

    @PostMapping
    public ResponseEntity<Integer> createActivity(@RequestBody ActivityRequest request){
        return ResponseEntity.ok(activityService.createActivity(request));
    }

    @PutMapping("/{activity-id}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable("activity-id") Integer activityId, @RequestBody ActivityRequest request,
                                                           Authentication user){
        return ResponseEntity.accepted().body(activityService.updateActivity(activityId, request, user));
    }

    //TODO
    @PutMapping("/create-group")
    public ResponseEntity<String> createGroup(@RequestBody String group, Authentication user){
        return ResponseEntity.accepted().body(activityService.createGroup(group, user));
    }

    //TODO
    @PutMapping("/add-to-group/{activity-id}")
    public ResponseEntity<String> addToGroup(@RequestBody String group, @PathVariable("activity-id")Integer activityId, Authentication user){
        return ResponseEntity.accepted().body(activityService.addToGroup(group, activityId, user));
    }

    @PostMapping("/copy-activity/{activity-id}")
    public ResponseEntity<ActivityResponse> copyActivityToUser(@PathVariable("activity-id") Integer activityId, Authentication user){
        return ResponseEntity.ok(activityService.copyActivityToUser(activityId, user));
    }

    @DeleteMapping("/{activity-id}")
    public ResponseEntity deleteOriginalActivityById(@PathVariable("activity-id") Integer activityId, Authentication user){
        return activityService.deleteOriginalActivityById(activityId, user);
    }

    @DeleteMapping("/remove/{activity-id}")
    public ResponseEntity removeFromUserLibrary(@PathVariable("activity-id") Integer activityId, Authentication user){
        return activityService.removeFromUserLibrary(activityId, user);
    }
}
