package com.miroslav.acitivity_tracker.group.service;

import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapper;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapperImpl;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.group.dto.GroupRequest;
import com.miroslav.acitivity_tracker.group.dto.GroupResponse;
import com.miroslav.acitivity_tracker.group.mapper.GroupMapper;
import com.miroslav.acitivity_tracker.group.mapper.GroupMapperImpl;
import com.miroslav.acitivity_tracker.group.model.Group;
import com.miroslav.acitivity_tracker.group.repository.GroupRepository;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Mock
    private UserContext userContext;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private GroupRepository groupRepository;
    @Spy
    private GroupMapper groupMapper = new GroupMapperImpl();
    @Spy
    private ActivityMapper activityMapper = new ActivityMapperImpl();
    @InjectMocks
    private GroupService groupService;

    GroupServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    private User user;
    private Profile profile;
    private Group group1;
    private Group group2;
    private Activity activity;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUserId(1);

        profile = new Profile();
        profile.setProfileId(1);
        profile.setUser(user);

        group1 = Group.builder()
                .groupId(1)
                .name("Group1")
                .profile(profile)
                .activities(new ArrayList<>())
                .build();
        group2 = Group.builder()
                .groupId(2)
                .name("Group2")
                .profile(profile)
                .activities(new ArrayList<>())
                .build();

        activity = Activity.builder()
                .name("activity")
                .profile(profile)
                .build();

    }

    @Test
    public void should_find_group_by_id() {
        when(groupRepository.findById(1)).thenReturn(Optional.of(group1));

        GroupResponse response = groupService.findById(1);

        assertNotNull(response);
        assertEquals("Group1", response.name());
        verify(groupRepository, times(1)).findById(1);
    }

    @Test
    public void should_throw_exception_when_group_not_found() {
        when(groupRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> groupService.findById(1));
    }

    @Test
    public void should_get_all_groups_with_pagination() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Group> groupPage = new PageImpl<>(List.of(group1, group2), pageable, 2);
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(groupRepository.findAllByProfileProfileId(1, pageable)).thenReturn(groupPage);

        Page<GroupResponse> response = groupService.getAllGroups(pageable);

        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals("Group1", response.getContent().get(0).name());
        verify(groupRepository, times(1)).findAllByProfileProfileId(1, pageable);
    }

    @Test
    public void should_find_all_activities_by_group() {
        group1.getActivities().add(activity);
        when(groupRepository.findById(1)).thenReturn(Optional.of(group1));

        List<ActivityResponse> response = groupService.findAllActivitiesByGroup(1);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("activity", response.get(0).name());
    }

    @Test
    public void should_create_group() {
        GroupRequest request = new GroupRequest("New Group");
        Group newGroup = Group.builder()
                        .name(request.name())
                        .profile(profile)
                        .build();

        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(any())).thenReturn(Optional.of(profile));
        when(groupRepository.findGroupByProfileProfileIdAndName(1, "New Group")).thenReturn(Optional.empty());
        when(groupRepository.save(any(Group.class))).thenReturn(newGroup);

        String groupName = groupService.createGroup(request);

        assertEquals("New Group", groupName);
        verify(groupRepository, times(1)).save(any(Group.class));
    }

    @Test
    public void should_throw_exception_when_group_name_exists() {
        GroupRequest request = new GroupRequest("Test Group");
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
        when(groupRepository.findGroupByProfileProfileIdAndName(1, "Test Group")).thenReturn(Optional.of(group1));

        assertThrows(ActionNotAllowed.class, () -> groupService.createGroup(request));
    }

    @Test
    public void should_add_activity_to_group() {
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));
        when(groupRepository.findById(1)).thenReturn(Optional.of(group1));
        when(groupRepository.save(any(Group.class))).thenReturn(group1);

        GroupResponse response = groupService.addActivityToGroup(1, 1);

        assertNotNull(response);
        assertTrue(group1.getActivities().contains(activity));
        verify(groupRepository, times(1)).save(group1);
    }

    @Test
    public void should_throw_exception_when_activity_already_in_group() {
        group1.getActivities().add(activity);
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));
        when(groupRepository.findById(1)).thenReturn(Optional.of(group1));

        assertThrows(ActionNotAllowed.class, () -> groupService.addActivityToGroup(1, 1));
    }

    @Test
    public void should_remove_activity_from_group() {
        group1.getActivities().add(activity);
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));
        when(groupRepository.findById(1)).thenReturn(Optional.of(group1));
        when(groupRepository.save(any(Group.class))).thenReturn(group1);

        GroupResponse response = groupService.removeActivityFromGroup(1, 1);

        assertNotNull(response);
        assertFalse(group1.getActivities().contains(activity));
        verify(groupRepository, times(1)).save(group1);
    }

    @Test
    public void should_throw_exception_when_activity_not_in_group() {
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));
        when(groupRepository.findById(1)).thenReturn(Optional.of(group1));

        assertThrows(ActionNotAllowed.class, () -> groupService.removeActivityFromGroup(1, 1));
    }

    @Test
    public void should_update_group() {
        GroupRequest request = new GroupRequest("Updated Group");
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(groupRepository.findGroupByProfileProfileIdAndName(1, "Updated Group")).thenReturn(Optional.empty());
        when(groupRepository.findById(1)).thenReturn(Optional.of(group1));
        when(groupRepository.save(any(Group.class))).thenReturn(group1);

        GroupResponse response = groupService.updateGroup(1, request);

        assertNotNull(response);
        assertEquals("Updated Group", response.name());
        verify(groupRepository, times(1)).save(group1);
    }

    @Test
    public void should_throw_exception_when_updating_to_existing_group_name() {
        GroupRequest request = new GroupRequest("Test Group");
        when(userContext.getAuthenticatedUser()).thenReturn(user);
        when(groupRepository.findGroupByProfileProfileIdAndName(1, "Test Group")).thenReturn(Optional.of(group1));

        assertThrows(ActionNotAllowed.class, () -> groupService.updateGroup(1, request));
    }

    @Test
    public void should_delete_group() {
        when(groupRepository.findById(1)).thenReturn(Optional.of(group1));
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        String result = groupService.deleteGroup(1);

        assertEquals("Group deleted successfully", result);
        verify(groupRepository, times(1)).delete(group1);
    }

    @Test
    public void should_throw_exception_when_deleting_group_not_owned_by_user() {
        Profile otherProfile = new Profile();
        otherProfile.setProfileId(2);
        group1.setProfile(otherProfile);
        when(groupRepository.findById(1)).thenReturn(Optional.of(group1));
        when(userContext.getAuthenticatedUser()).thenReturn(user);

        assertThrows(ActionNotAllowed.class, () -> groupService.deleteGroup(1));
    }



}
