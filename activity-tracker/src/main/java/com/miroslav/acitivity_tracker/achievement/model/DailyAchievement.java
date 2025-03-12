package com.miroslav.acitivity_tracker.achievement.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "_daily_achievement")
public class DailyAchievement extends TypeSuperclass{

    private int currentStreak;
    private int biggestStreak;
}
