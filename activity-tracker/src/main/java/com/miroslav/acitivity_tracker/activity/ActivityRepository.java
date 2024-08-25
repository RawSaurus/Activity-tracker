package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("""
            SELECT activity
            FROM Activity activity
            WHERE activity.name = name
            """)
    Optional<List<Activity>> findAllByName(String name);

    @Query("""
            SELECT activity
            FROM Activity activity
            WHERE activity.category = category
            """)
    Optional<List<Activity>> findAllByCategory(String category);

}
