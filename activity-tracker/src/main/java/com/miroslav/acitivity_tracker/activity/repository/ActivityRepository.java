package com.miroslav.acitivity_tracker.activity.repository;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    Optional<Activity> findByName(String name);
    List<Activity> findAllByName(String name);
    Page<Activity> findAllByProfileProfileId(Integer profileId, Pageable pageable);

//    List<Activity> findAllByCategory(Category category);

    Optional<Activity> findByProfileProfileIdAndName(Integer profileId, String name);

    Optional<Activity> findActivityByActivityIdAndProfileProfileId(Integer activityId, Integer profileId);


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
