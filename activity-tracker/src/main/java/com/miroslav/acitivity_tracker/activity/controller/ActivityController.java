package com.miroslav.acitivity_tracker.activity.controller;

import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.group.dto.GroupRequest;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.activity.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("activity")
public class ActivityController {

    private final ActivityService activityService;


    //TODO add authorization

    @GetMapping("/{activity-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ActivityResponse> findById(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findById(activityId));
    }

    @GetMapping("/library/{activity-id}")
    public ResponseEntity<ActivityResponse> findInUserLibrary(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findInUserLibrary(activityId));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ActivityResponse> findInUserLibraryByName(@PathVariable("name") String name){
        return ResponseEntity.ok(activityService.findInUserLibraryByName(name));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ActivityResponse>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "name") String sortBy,
            @RequestParam(value = "sort-direction", defaultValue = "asc") String sortDirection){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(activityService.findAll(pageable));
    }

    //new
    @GetMapping("/links/{activity-id}")
    public ResponseEntity<EntityModel<ActivityResponse>> findByIdWithLinks(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.findByIdWithLinks(activityId));
    }

    @GetMapping("/all/links")
    public ResponseEntity<Page<EntityModel<ActivityResponse>>> findAllWithLinks(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "name") String sortBy,
            @RequestParam(value = "sort-direction", defaultValue = "asc") String sortDirection){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(activityService.findAllWithLinks(pageable));
    }

//    @GetMapping("/category/{category}")
//    public ResponseEntity<List<ActivityResponse>> findAllByCategory(@PathVariable("category") Category activityCategory){
//        return ResponseEntity.ok(activityService.findAllByCategory(activityCategory));
//    }

    @PostMapping
    public ResponseEntity<Integer> createActivity(@RequestBody @Valid ActivityRequest request){
        return ResponseEntity.ok(activityService.createActivity(request));
    }

    @PutMapping("/{activity-id}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable("activity-id") Integer activityId, @RequestBody ActivityRequest request){
        return ResponseEntity.accepted().body(activityService.updateActivity(activityId, request));
    }

    @PatchMapping("/{activity-id}")
    public ResponseEntity<?> addImage(@PathVariable("activity-id") Integer activityId, @RequestParam MultipartFile file){
        activityService.addImage(activityId, file);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{activity-id}")
    public ResponseEntity<?> deleteActivityById(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(activityService.deleteActivityById(activityId));
    }
}
