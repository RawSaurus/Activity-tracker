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
@Entity(name = "_goal_achievement")
public class GoalAchievement extends TypeSuperclass {

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date deadline;
}
