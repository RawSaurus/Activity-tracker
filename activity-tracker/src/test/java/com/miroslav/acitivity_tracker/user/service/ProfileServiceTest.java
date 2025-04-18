package com.miroslav.acitivity_tracker.user.service;

import com.miroslav.acitivity_tracker.file.model.File;
import com.miroslav.acitivity_tracker.file.service.FileService;
import com.miroslav.acitivity_tracker.role.model.Role;
import com.miroslav.acitivity_tracker.role.repository.RoleRepository;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.dto.ProfileResponse;
import com.miroslav.acitivity_tracker.user.dto.UserRequest;
import com.miroslav.acitivity_tracker.user.mapper.ProfileMapper;
import com.miroslav.acitivity_tracker.user.mapper.ProfileMapperImpl;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import com.miroslav.acitivity_tracker.user.repository.UserRepository;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserContext userContext;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private FileService fileService;
    @Spy
    private ProfileMapper profileMapper = new ProfileMapperImpl();
    @InjectMocks
    private ProfileService profileService;

    ProfileServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    private User user1;
    private User user2;
    private Profile profile1;
    private Profile profile2;

    @BeforeEach
    public void setUp(){
        user1 = User.builder()
                .userId(1)
                .username("user1")
                .email("email1")
                .build();
        user2 = User.builder()
                .userId(2)
                .username("user2")
                .email("email2")
                .build();
        profile1 = Profile.builder()
                .profileId(1)
                .user(user1)
                .username("user1")
                .activities(new ArrayList<>())
                .events(new ArrayList<>())
                .build();
        profile2 = Profile.builder()
                .profileId(2)
                .user(user2)
                .username("user2")
                .activities(new ArrayList<>())
                .events(new ArrayList<>())
                .build();

        user1.setProfile(profile1);
        user2.setProfile(profile2);
    }

    @Test
    public void should_find_profile_by_id(){
        when(profileRepository.findById(profile1.getProfileId())).thenReturn(Optional.of(profile1));

        Profile response = profileService.getProfile(profile1.getProfileId());

        assertNotNull(response);
        assertEquals(profile1.getUsername(),response.getUsername());
        verify(profileRepository, times(1)).findById(profile1.getProfileId());
    }

    @Test
    public void should_find_profile_by_mail(){
        when(profileRepository.findByUserEmail(profile1.getUser().getEmail())).thenReturn(Optional.of(profile1));

        Profile response = profileService.getProfileByMail(user1.getEmail());

        assertNotNull(response);
        assertEquals(user1.getEmail(), response.getUser().getEmail());
    }

    @Test
    public void should_get_own_profile(){
        when(profileRepository.findById(profile1.getProfileId())).thenReturn(Optional.of(profile1));
        when(userContext.getAuthenticatedUser()).thenReturn(user1);

        ProfileResponse response = profileService.getOwnProfile();

        assertNotNull(response);
        assertEquals(profile1.getUsername(), response.username());
    }

    @Test
    public void should_create_profile(){
        UserRequest request = new UserRequest("first", "last", "mail", "1234", "TEST");
        Role role = Role.builder()
                .roleId(1)
                .name("TEST")
                .build();

        when(roleRepository.findByName(any())).thenReturn(Optional.of(role));
        when(userRepository.save(any())).thenReturn(user1);
        when(profileRepository.save(any())).thenReturn(profile1);

        Integer response = profileService.createProfile(request);

        assertNotNull(response);
        assertEquals(1, response);
    }

    @Test
    public void should_disable_account(){

        when(userRepository.findById(profile1.getProfileId())).thenReturn(Optional.of(user1));
        when(userRepository.save(user1)).thenReturn(user1);

        Integer response = profileService.disableAccount(profile1.getProfileId());

        assertNotNull(response);
        assertTrue(user1.isAccountLocked());
        assertFalse(user1.isEnabled());
    }

    @Test
    public void should_add_image(){
        File file = File.builder()
                .name("file")
                .build();

        when(profileRepository.findById(1)).thenReturn(Optional.of(profile1));
        when(fileService.uploadFile(any())).thenReturn(file);
        when(profileRepository.save(profile1)).thenReturn(profile1);
        when(userContext.getAuthenticatedUser()).thenReturn(user1);

        profileService.addImage( null);

        assertEquals(file.getName(), profile1.getPicture().getName());
    }

    @Test
    public void should_delete_account(){
        when(profileRepository.findById(profile1.getProfileId())).thenReturn(Optional.of(profile1));

        String result = profileService.deleteAccount(profile1.getProfileId());

        assertNotNull(result);
        assertEquals("Account deleted successfully", result);
//        verify(profileRepository, times(1)).delete(profile1);
    }

    @Test
    public void should_delete_own_account(){
        when(userContext.getAuthenticatedUser()).thenReturn(user1);
        when(profileRepository.findById(user1.getProfile().getProfileId())).thenReturn(Optional.of(profile1));

        String result = profileService.deleteOwnAccount();

        assertNotNull(result);
        assertEquals("Account deleted successfully", result);
//        verify(profileRepository, times(1)).delete(profile1);
    }

}
