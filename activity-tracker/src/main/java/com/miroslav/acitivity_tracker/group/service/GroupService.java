package com.miroslav.acitivity_tracker.group.service;

import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapper;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.repository.ActivityRepository;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import com.miroslav.acitivity_tracker.group.dto.GroupRequest;
import com.miroslav.acitivity_tracker.group.dto.GroupResponse;
import com.miroslav.acitivity_tracker.group.mapper.GroupMapper;
import com.miroslav.acitivity_tracker.group.model.Group;
import com.miroslav.acitivity_tracker.group.repository.GroupRepository;
import com.miroslav.acitivity_tracker.security.UserContext;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final UserContext userContext;
    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final ActivityMapper activityMapper;

    public GroupResponse findById(Integer groupId) {
        return groupMapper.toResponse(groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found")));
    }

    public List<GroupResponse> getAllGroups() {
        return groupRepository.findAllByProfileProfileId(userContext.getAuthenticatedUser().getUserId())
                .stream()
                .map(groupMapper::toResponse)
                .collect(Collectors.toList());
    }
    public List<ActivityResponse> findAllActivitiesByGroup(Integer groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"))
                .getActivities()
                .stream()
                .map(activityMapper::toResponse)
                .collect(Collectors.toList());
    }

    //TODO test
    public String createGroup(GroupRequest request) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
//      check if profile already doesn't contain group with same name
        //
//      if yes throw action not allowed and if not create new one
        if(groupRepository.findGroupByProfileProfileIdAndName(profile.getProfileId(), request.name()).isPresent()){
            throw new ActionNotAllowed("Group with this name already exists");
        }
        Group group = Group.builder()
                .name(request.name())
                .profile(profile)
                .build();

        return groupRepository.save(group).getName();
    }

    //TODO test
    public GroupResponse addActivityToGroup(Integer groupId, Integer activityId) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
        if(!activity.getProfile().getProfileId().equals(profile.getProfileId())){
            throw new ActionNotAllowed("You don't have this activity in your library");
        }
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        if(group.getActivities().contains(activity)){
            throw new ActionNotAllowed(activity.getName() + " is already part of " + group.getName());
        }
        group.getActivities().add(activity);

        return groupMapper.toResponse(groupRepository.save(group));
    }


    public GroupResponse removeActivityFromGroup(Integer groupId, Integer activityId) {
        Profile profile = profileRepository.findById(userContext.getAuthenticatedUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
        if(!activity.getProfile().getProfileId().equals(profile.getProfileId())){
            throw new ActionNotAllowed("You don't have this activity in your library");
        }
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        if(!group.getActivities().contains(activity)){
            throw new ActionNotAllowed(activity.getName() + " isn't part of " + group.getName());
        }
        group.getActivities().remove(activity);

        return groupMapper.toResponse(groupRepository.save(group));
    }

    public GroupResponse updateGroup(Integer groupId, GroupRequest request) {
        if(groupRepository.findGroupByProfileProfileIdAndName(userContext.getAuthenticatedUser().getUserId(), request.name()).isPresent()){
            throw new ActionNotAllowed("Group with this name already exists");
        }
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        groupMapper.updateToEntity(group, request);

        return groupMapper.toResponse(groupRepository.save(group));
    }

    public String deleteGroup(Integer groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        if(!group.getProfile().getProfileId().equals(userContext.getAuthenticatedUser().getUserId())){
            throw new ActionNotAllowed("You cannot delete this group");
        }

        groupRepository.delete(group);
        return "Group deleted successfully";
    }

}
