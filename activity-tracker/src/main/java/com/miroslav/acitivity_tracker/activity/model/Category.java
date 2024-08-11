package com.miroslav.acitivity_tracker.activity.model;

import lombok.Getter;

@Getter
public enum Category {

    ACTIVITY("activities"),
    SPORT("sport");


    private final String name;

    Category(String name){
        this.name = name;
    }
}
