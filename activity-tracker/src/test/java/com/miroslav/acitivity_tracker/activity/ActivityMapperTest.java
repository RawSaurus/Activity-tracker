package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.dto.ActivityResponse;
import com.miroslav.acitivity_tracker.activity.mapper.ActivityMapper;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ActivityMapperTest {

    @Autowired
    private ActivityMapper activityMapper;


    @Test
    public void should_properly_map_entity_to_response(){

        Activity activity = Activity.builder()
                .name("activity")
                .info("short info")
                .type("type")
                .category(Category.SPORT)
                .build();

        ActivityResponse response = activityMapper.toResponse(activity);

        assertNotNull(response);
    }
}
