package com.miroslav.acitivity_tracker.achievement.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class TypeSuperclass {

    @Id
    private Integer typeAchievementId;
    @Column(name="set_xp_gain_for_one_unit")
    private int setXPGain;
    @Column(name="total_xp")
    private int totalXP;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "achievement_id", referencedColumnName = "achievementId")
    private Achievement achievement;
}
