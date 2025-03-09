package com.miroslav.acitivity_tracker.achievement.model;


import lombok.Getter;

@Getter
public enum Type {

    GOAL("goal"),
    DAILY("daily"),
    AMOUNT("amount");

    private final String name;

    Type(String name){
        this.name = name;
    }
}
