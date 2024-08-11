package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("activity")
public class ActivityController {

    private final ActivityService activityService;


    //TODO create group -> group entity
    //TODO post activity to market

    @GetMapping("/{activity-id}")
    public ResponseEntity<ActivityResponse> findById(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findById(activityId));
    }

    @GetMapping("/library/{activity-id}")//TODO
    public ResponseEntity<ActivityResponse> findInUserLibrary(@PathVariable("activity-id") Integer activityId, Authentication user){
        return ResponseEntity.ok(activityService.findInUserLibrary(activityId, user));
    }
    @GetMapping("/library/name/{name}")//TODO
    public ResponseEntity<ActivityResponse> findInUserLibraryByName(@PathVariable("name") String name, Authentication user){
        return ResponseEntity.ok(activityService.findInUserLibraryByName(name, user));
    }

    @GetMapping("/market/{activity-id}")//TODO
    public ResponseEntity<ActivityResponse> findInMarket(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findInMarket(activityId));
    }

    @GetMapping("/market/name/{name}")//TODO
    public ResponseEntity<ActivityResponse> findInMarketByName(@PathVariable("name") String name){
        return ResponseEntity.ok(activityService.findInMarketByName(name));
    }

    @GetMapping("/category/{activity-category}")
    public ResponseEntity<List<ActivityResponse>> findAllByCategory(@PathVariable("activity-category") String activityCategory){
        return ResponseEntity.ok(activityService.findAllByCategory(activityCategory));
    }

    @PostMapping
    public ResponseEntity<Integer> createActivity(@RequestBody ActivityRequest request, Authentication user){
        return ResponseEntity.ok(activityService.createActivity(request, user));
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
    @PutMapping("/add-to-group")
    public ResponseEntity<String> addToGroup(@RequestBody String group, Authentication user){
        return ResponseEntity.accepted().body(activityService.addToGroup(group, user));
    }

    @PutMapping("/copy-activity/{activity-id}")
    public ResponseEntity<ActivityResponse> copyActivityToUser(@PathVariable("activity-id") Integer activityId, Authentication user){
        return ResponseEntity.ok(activityService.copyActivityToUser(activityId, user));
    }

    @DeleteMapping("/{activity-id}")
    public ResponseEntity deleteOriginalActivityById(@PathVariable("activity-id") Integer activityId, Authentication user){
        return activityService.deleteOriginalActivityById(activityId, user);
    }

    @DeleteMapping("remove/{activity-id}")
    public ResponseEntity removeFromUserLibrary(@PathVariable("activity-id") Integer activityId, Authentication user){
        return activityService.removeFromUserLibrary(activityId, user);
    }
}
