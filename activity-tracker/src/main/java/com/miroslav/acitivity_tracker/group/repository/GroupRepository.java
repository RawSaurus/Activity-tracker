package com.miroslav.acitivity_tracker.group.repository;


import com.miroslav.acitivity_tracker.group.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {

//    @Query("""
//            SELECT g
//            FROM com.miroslav.acitivity_tracker.activity.model.Group g
//            WHERE g.profile.profileId = :profileId
//            AND g.name = :name
//            """
//    )
//    Optional<Group> existsInProfile(@Param("profileId")Integer profileId,@Param("name") String name);

    Optional<Group> findGroupByProfileProfileIdAndName(Integer profileId, String name);

//    @Query("""
//            SELECT g
//            FROM Group g
//            WHERE g.profile.profileId = :profileId
//            """)
    List<Group> findAllByProfileProfileId(Integer profileId);
    Page<Group> findAllByProfileProfileId(Integer profileId, Pageable pageable);
}
