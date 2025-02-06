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

//    @Id
//    @GeneratedValue
//    private Integer amountAchievementId;
//    private double amount;
//    @Column(name="set_xp_gain_for_one_unit")
//    private int setXPGainForOneUnit;
//    @Column(name="total_xp")
//    private int totalXP;
    private String unit;
}
