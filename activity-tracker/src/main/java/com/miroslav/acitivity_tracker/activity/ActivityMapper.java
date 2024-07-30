package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityResponse toResponse(Activity activity){
        return new ActivityResponse(
                activity.getName(),
//                activity.getGroup(),
                activity.getInfo(),
                activity.getType(),
                activity.getCategory(),
                activity.getRating(),
                activity.getDownloads()
        );
    }

    public Activity toEntity(ActivityResponse activityResponse){
        return Activity.builder()
                .name(activityResponse.name())
//                .group(activityResponse.group())
                .info(activityResponse.info())
                .type(activityResponse.type())
                .category(activityResponse.category())
                .rating(activityResponse.rating())
                .downloads(activityResponse.downloads())
                .build();
    }

    public Activity toEntity(ActivityRequest activityRequest){
        return Activity.builder()
                .name(activityRequest.name())
                .info(activityRequest.info())
                .type(activityRequest.type())
                .category(activityRequest.category())
                .build();
    }

}
