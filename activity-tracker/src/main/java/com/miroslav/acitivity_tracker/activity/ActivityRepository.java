package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("""
            SELECT activity
            FROM Activity activity
            WHERE activity.name = name
            """)
    Optional<Activity> findByName(String name);

    @Query("""
            SELECT activity
            FROM Activity activity
            WHERE activity.category = category
            """)
    Optional<List<Activity>> findAllByCategory(String category);
}
