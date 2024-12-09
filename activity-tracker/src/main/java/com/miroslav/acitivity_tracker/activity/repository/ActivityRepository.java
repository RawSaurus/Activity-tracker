package com.miroslav.acitivity_tracker.activity.repository;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findAllByName(String name);

    List<Activity> findAllByCategory(String category);

    Optional<Activity> findActivityByActivityIdAndProfileProfileId(Integer activityId, Integer profileId);

    Optional<Activity> findActivityByNameAndCreatorId(String name, Integer creatorId);

//    @Query("""
//            Select activity
//            FROM Activity activity
//            WHERE activity.activityId = activityId
//            AND activity.isPrivate = false
//            """)
    Optional<Activity> findActivityByActivityIdAndIsPrivate(Integer activityId, boolean isPrivate);
    @Query("""
            Select activity
            FROM Activity activity
            WHERE activity.name = name
            AND activity.isPrivate = false
            """)
    List<Activity> findInMarketByName(String name);

}