package com.miroslav.acitivity_tracker.calendar.module;

import lombok.Getter;

@Getter
public enum EventType {
    SESSION("session"),
    ACHIEVEMENT_DONE("achievement-done"),
    PLAN("plan");

    EventType(String name) {
        this.name = name;
    }

    private final String name;

}
