package com.miroslav.acitivity_tracker.group.repository;

import com.miroslav.acitivity_tracker.RepositoryTestConfig;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.group.model.Group;
import com.miroslav.acitivity_tracker.user.model.Profile;
import com.miroslav.acitivity_tracker.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = RepositoryTestConfig.class)
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Group group1;
    private Group group2;
    private Profile profile;
    @BeforeEach
    public void setUp(){
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setFirstName("first");
        user.setLastName("last");
        User savedUser = testEntityManager.persist(user);
        Profile profileToSave = Profile.builder()
                .profileId(savedUser.getUserId())
                .user(savedUser)
                .username("username")
                .build();
        profile = testEntityManager.persist(profileToSave);
        group1 = Group.builder()
                .name("group1")
                .activities(new ArrayList<>())
                .profile(profile)
                .build();
        group2 = Group.builder()
                .name("group2")
                .activities(new ArrayList<>())
                .profile(profile)
                .build();

        groupRepository.saveAll(List.of(group1, group2));
    }

    @AfterEach
    public void tearDown(){
        groupRepository.deleteAll();
    }

    @Test
    public void should_find_group_by_profile_id_and_name(){
        Optional<Group> foundGroup = groupRepository.findGroupByProfileProfileIdAndName(group1.getProfile().getProfileId(), group1.getName());

        assertNotNull(foundGroup);
        assertEquals(group1.getName(), foundGroup.get().getName());
    }

    @Test
    public void should_find_list_of_groups_by_profile_id(){
        List<Group> foundList = groupRepository.findAllByProfileProfileId(profile.getProfileId());

        assertNotNull(foundList);
        assertEquals(2, foundList.size());
    }

    @Test
    public void should_find_page_of_groups_by_profile_id(){
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "name");
        Page<Group> page = groupRepository.findAllByProfileProfileId(profile.getProfileId(), pageable);

        assertNotNull(page.getContent());
        assertEquals(2, page.getContent().size());
    }

    @Test
    public void should_find_by_id(){
        Optional<Group> foundGroup = groupRepository.findById(group1.getGroupId());

        assertTrue(foundGroup.isPresent());
        assertEquals(group1.getName(), foundGroup.get().getName());
    }

    @Test
    public void should_save_group(){
        Group groupToSave = Group.builder()
                .name("saved")
                .build();

        Group savedGroup = groupRepository.save(groupToSave);

        assertNotNull(savedGroup);
        assertEquals(groupToSave.getName(), savedGroup.getName());
    }

    @Test
    public void should_delete_group(){
        groupRepository.delete(group2);

        Optional<Group> deletedGroup = groupRepository.findById(group2.getGroupId());

        assertTrue(deletedGroup.isEmpty());
    }
}
