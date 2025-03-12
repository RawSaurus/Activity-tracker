package com.miroslav.acitivity_tracker.achievement.dto;


import com.miroslav.acitivity_tracker.achievement.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementResponseV2<T extends TypeSuperclass> {

    private String name;
    private String info;
    private Type type;
    private int xp;
    private boolean finished;
    private String unit;
    private Date deadline;
    private int currentStreak;
    private int biggestStreak;

    public AchievementResponseV2(String name, String info, Type type, int xp, T typeClass){
        this.name = name;
        this.info = info;
        this.type = type;
        this.xp = xp;
        setTypeData(typeClass);
    }
    public AchievementResponseV2(String name, String info, Type type, int xp){
        this.name = name;
        this.info = info;
        this.type = type;
        this.xp = xp;
    }

    public AchievementResponseV2 toResponse(Achievement achievement, T typeClass){
        return new AchievementResponseV2(
                achievement.getName(),
                achievement.getInfo(),
                achievement.getType(),
                achievement.getXp(),
                typeClass
                );
    }

    public void setTypeData(T typeClass){
        if(typeClass.getClass().equals(AmountAchievement.class)){
            this.unit = ((AmountAchievement) typeClass).getUnit();
        }else if(typeClass.getClass().equals(GoalAchievement.class)){
            this.deadline = ((GoalAchievement) typeClass).getDeadline();
        }else if(typeClass.getClass().equals(DailyAchievement.class)){
            this.currentStreak = ((DailyAchievement) typeClass).getCurrentStreak();
            this.biggestStreak = ((DailyAchievement) typeClass).getBiggestStreak();
        }
    }
}
