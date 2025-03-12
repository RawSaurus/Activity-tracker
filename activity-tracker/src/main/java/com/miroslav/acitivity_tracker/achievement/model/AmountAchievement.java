package com.miroslav.acitivity_tracker.achievement.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "_amount_achievement")
public class AmountAchievement extends TypeSuperclass {

    private String unit;
}
