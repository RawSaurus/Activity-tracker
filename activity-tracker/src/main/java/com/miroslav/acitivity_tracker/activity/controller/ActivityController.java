package com.miroslav.acitivity_tracker.activity.controller;

import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.group.dto.GroupRequest;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("activity")
public class ActivityController {

    private final ActivityService activityService;


    //TODO add authorization
    //TODO create tests

    @GetMapping("/{activity-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ActivityResponse> findById(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findById(activityId));
    }
    //TODO create findByName

    @GetMapping("/library/{activity-id}")//TODO
    public ResponseEntity<ActivityResponse> findInUserLibrary(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findInUserLibrary(activityId));
    }
//    @GetMapping("/library/name/{name}")//TODO
//    public ResponseEntity<ActivityResponse> findInUserLibraryByName(@PathVariable("name") String name){
//        return ResponseEntity.ok(activityService.findInUserLibraryByName(name));
//    }

//    @GetMapping("/market/{activity-id}")
//    public ResponseEntity<ActivityResponse> findInMarket(@PathVariable("activity-id") Integer activityId){
//        return ResponseEntity.ok(activityService.findInMarket(activityId));
//    }
//
//    @GetMapping("/market/name/{name}")
//    public ResponseEntity<List<ActivityResponse>> findInMarketByName(@PathVariable("name") String name){
//        return ResponseEntity.ok(activityService.findInMarketByName(name));
//    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ActivityResponse>> findAllByCategory(@PathVariable("category") Category activityCategory){
        return ResponseEntity.ok(activityService.findAllByCategory(activityCategory));
    }

    @PostMapping
    public ResponseEntity<Integer> createActivity(@RequestBody @Valid ActivityRequest request){
        return ResponseEntity.ok(activityService.createActivity(request));
    }

    @PutMapping("/{activity-id}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable("activity-id") Integer activityId, @RequestBody ActivityRequest request,
                                                           Authentication user){
        return ResponseEntity.accepted().body(activityService.updateActivity(activityId, request));
    }


//    @PostMapping("/copy-activity/{activity-id}")
//    public ResponseEntity<ActivityResponse> copyActivityToUser(@PathVariable("activity-id") Integer activityId){
//        return ResponseEntity.ok(activityService.copyActivityToUser(activityId));
//    }

    @DeleteMapping("/{activity-id}")
    public ResponseEntity deleteActivityById(@PathVariable("activity-id") Integer activityId){
        return activityService.deleteActivityById(activityId);
    }

//    @DeleteMapping("/remove/{activity-id}")
//    public ResponseEntity removeFromUserLibrary(@PathVariable("activity-id") Integer activityId){
//        return activityService.removeFromUserLibrary(activityId);
//    }
}
