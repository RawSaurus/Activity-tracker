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

//    @Id
//    @GeneratedValue
//    private Integer dailyAchievementId;
//    @Column(name="total_xp")
//    private int totalXP;
//    @Column(name="set_xp_gain")
//    private int setXPGain;
    private int currentStreak;
    private int biggestStreak;
//    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
//    @JoinColumn(name = "achievement_id", referencedColumnName = "achievementId")
//    private Achievement achievement;
}
