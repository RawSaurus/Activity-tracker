package com.miroslav.acitivity_tracker.achievement;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("achievement")
public class AchievementController {

    private final AchievementService achievementService;

    //TODO find achievement
    //TODO find all achievements by name
    //TODO create achievement
    //TODO update achievement
    //TODO delete achievement
    //TODO copy achievement to another activity
    //TODO create group -> group entity
}
