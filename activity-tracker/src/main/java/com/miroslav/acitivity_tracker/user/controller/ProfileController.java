package com.miroslav.acitivity_tracker.user.controller;

import com.miroslav.acitivity_tracker.user.dto.ProfileResponse;
import com.miroslav.acitivity_tracker.user.dto.UserRequest;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    /*TODO
    *  endpoints
    *       update user info
    *       change profile picture
    *       get picture
    *       get info
    *       delete account
    *       change password
    *       log out -> auth controller
    *       preferences ? (for later)
    *
    *       */
    //TODO create tests
    //TODO add authorization

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{profile-id}")
    public ResponseEntity<Profile> getProfile(@PathVariable("profile-id") Integer profileId){
        return ResponseEntity.ok(profileService.getProfile(profileId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/mail/{email}")
    public ResponseEntity<Profile> getProfileByMail(@PathVariable("email") String email){
        return ResponseEntity.ok(profileService.getProfileByMail(email));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<ProfileResponse> getOwnProfile(){
        return ResponseEntity.ok(profileService.getOwnProfile());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/links/{profile-id}")
    public ResponseEntity<EntityModel<ProfileResponse>> getProfileWithLinks(@PathVariable("profile-id")Integer profileId){
        return ResponseEntity.ok(profileService.getProfileWithLinks(profileId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Integer> createProfile(@RequestBody UserRequest request){
        return ResponseEntity.ok(profileService.createProfile(request));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/disable/{profile-id}")
    public ResponseEntity<Integer> disableAccount(@PathVariable("profile-id") Integer profileId){
        return ResponseEntity.ok(profileService.disableAccount(profileId));
    }

//    @PreAuthorize("hasAuthority('USER')")
//    @PutMapping("update/{profile-id}")
//    public ResponseEntity<ProfileResponse> updateAccount(@PathVariable("profile-id") Integer profileId, @RequestBody UserRequest request){
//        return ResponseEntity.ok(profileService.updateAccount(profileId, request));
//    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping
    public ResponseEntity<?> addImage(@RequestParam MultipartFile file){
        profileService.addImage(file);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{profile-id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("profile-id") Integer profileId){
        return ResponseEntity.ok(profileService.deleteAccount(profileId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOwnAccount(){
        return ResponseEntity.ok(profileService.deleteOwnAccount());
    }
}
