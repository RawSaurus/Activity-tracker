package com.miroslav.acitivity_tracker.template.controller;

import com.miroslav.acitivity_tracker.activity.dto.ActivityRequest;
import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.template.dto.TemplateRequest;
import com.miroslav.acitivity_tracker.template.dto.TemplateResponse;
import com.miroslav.acitivity_tracker.template.service.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("template")
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping("/{template-id}")
    public ResponseEntity<TemplateResponse> findTemplateById(@PathVariable("template-id") Integer templateId){
        return ResponseEntity.ok(templateService.findById(templateId));
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<TemplateResponse>> findAllTemplates(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "name") String sortBy,
            @RequestParam(value = "sort-direction", defaultValue = "asc") String sortDirection
    ){
        return ResponseEntity.ok(templateService.findAll(PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortBy)));
    }

    @GetMapping("/links/{template-id}")
    public ResponseEntity<EntityModel<TemplateResponse>> findTemplateWithLinks(@PathVariable("template-id") Integer templateId){
        return ResponseEntity.ok(templateService.findByIdWithLinks(templateId));
    }

    @GetMapping("/links/all")
    public ResponseEntity<Page<EntityModel<TemplateResponse>>> findAllTemplatesWithLinks(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "name") String sortBy,
            @RequestParam(value = "sort-direction", defaultValue = "asc") String sortDirection
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortBy);
        return ResponseEntity.ok(templateService.findAllWithLinks(pageable));
    }

    @PostMapping
    public ResponseEntity<Integer> createNewTemplate(@RequestBody @Valid TemplateRequest request){
        return ResponseEntity.ok(templateService.createNewTemplate(request));
    }

    @PostMapping("/{activity-id}")
    public ResponseEntity<Integer> postTemplateFromExistingActivity(@PathVariable("activity-id") Integer activityId){
        return ResponseEntity.ok(templateService.postTemplateFromExistingActivity(activityId));
    }

    @PutMapping("/{template-id}")
    public ResponseEntity<TemplateResponse> updateTemplate(@PathVariable("template-id") Integer templateId, @RequestBody @Valid TemplateRequest request){
        return ResponseEntity.ok(templateService.updateTemplate(templateId, request));
    }

    @PatchMapping("/{template-id}")
    public ResponseEntity<?> addImage(@PathVariable("template-id") Integer templateId, @RequestParam MultipartFile file){
        templateService.addImage(templateId, file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{template-id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable("template-id") Integer templateId){
        return ResponseEntity.ok(templateService.deleteTemplate(templateId));
    }
}
