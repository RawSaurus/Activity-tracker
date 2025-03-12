package com.miroslav.acitivity_tracker.achievement.model;

public enum XPGain {

    VERRY_SMALL(0.25),
    SMALL(0.5),
    NORMAL(1),
    BIG(1.25),
    VERY_BIG(1.5);

    private final double multiplier;

    XPGain(double multiplier){
        this.multiplier = multiplier;
    }
}
