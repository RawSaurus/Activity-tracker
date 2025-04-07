package com.miroslav.acitivity_tracker.file.controller;

import com.miroslav.acitivity_tracker.file.dto.FileResponse;
import com.miroslav.acitivity_tracker.file.model.File;
import com.miroslav.acitivity_tracker.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @GetMapping("/id/{file-id}")
    public ResponseEntity<FileResponse> getFile(@PathVariable("file-id") Integer fileId){
        return ResponseEntity.ok(fileService.findById(fileId));
    }

    @GetMapping("/{file-code}")
    public ResponseEntity<FileResponse> getFile(@PathVariable("file-code") String fileCode){
        return ResponseEntity.ok(fileService.findByFileCode(fileCode));
    }

    @GetMapping("/download/id/{file-id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("file-id") Integer fileId){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(fileService.downloadFile(fileId));
    }

    @GetMapping("/download/{file-code}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("file-code") String fileCode){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(fileService.downloadFile(fileCode));
    }

    @PostMapping
    public ResponseEntity<File> uploadFile(@RequestParam MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.uploadFile(file));
    }

    @PutMapping("/{file-code}")
    public ResponseEntity<File> updateFile(@PathVariable("file-code") String fileCode, @RequestParam MultipartFile file){
        return ResponseEntity.ok(fileService.updateFile(fileCode, file));
    }

    @DeleteMapping("/id/{file-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteFile(@PathVariable("file-id") Integer fileId){
        fileService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{file-code}")
    public ResponseEntity<?> deleteFile(@PathVariable("file-code")String fileCode){
        fileService.deleteFile(fileCode);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-dir")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteDir(){
    fileService.deleteDir(new java.io.File("Files-Upload"));
    return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteAll(){
        fileService.deleteAll();
        return ResponseEntity.ok().build();
    }

}
