package com.miroslav.acitivity_tracker.group.controller;

import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.group.dto.GroupRequest;
import com.miroslav.acitivity_tracker.group.dto.GroupResponse;
import com.miroslav.acitivity_tracker.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("group")
public class GroupController {

    private final GroupService groupService;


    @GetMapping("/{group-id}")
    public ResponseEntity<GroupResponse> getGroup(@PathVariable("group-id")Integer groupId){
        return ResponseEntity.ok(groupService.findById(groupId));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups(){
        return ResponseEntity.ok(groupService.getAllGroups());
    }
    @GetMapping("/{group-id}/activities")
    public ResponseEntity<List<ActivityResponse>> findAllActivitiesByGroup(@PathVariable("group-id") Integer groupId){
        return ResponseEntity.ok(groupService.findAllActivitiesByGroup(groupId));
    }

    @PostMapping("/create-group")
    public ResponseEntity<String> createGroup(@RequestBody GroupRequest request){
        return ResponseEntity.accepted().body(groupService.createGroup(request));
    }

    @PutMapping("/add-to-group/{group-id}/{activity-id}")
    public ResponseEntity<GroupResponse> addActivityToGroup(@PathVariable("group-id")Integer groupId, @PathVariable("activity-id")Integer activityId){
        return ResponseEntity.accepted().body(groupService.addActivityToGroup(groupId, activityId));
    }

    @PutMapping("/remove-from-group/{group-id}/{activity-id}")
    public ResponseEntity<GroupResponse> removeActivityFromGroup(@PathVariable("group-id")Integer groupId, @PathVariable("activity-id")Integer activityId){
        return ResponseEntity.ok(groupService.removeActivityFromGroup(groupId, activityId));
    }

    @PutMapping("/{group-id}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable("group-id")Integer groupId, @RequestBody GroupRequest request){
        return ResponseEntity.ok(groupService.updateGroup(groupId, request));
    }

    @DeleteMapping("/{group-id}")
    public ResponseEntity<String> deleteGroup(@PathVariable("group-id")Integer groupId){
        return ResponseEntity.ok(groupService.deleteGroup(groupId));
    }
}
