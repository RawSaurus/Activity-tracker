package com.miroslav.acitivity_tracker.achievement.dto;


import com.miroslav.acitivity_tracker.achievement.model.*;
import com.miroslav.acitivity_tracker.exception.ActionNotAllowed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementResponseV2 {

    private int achievementId;
    private String name;
    private String info;
    private Type type;
    private int xp;
    private boolean finished;
    private String unit;
    private Date deadline;
    private int currentStreak;
    private int biggestStreak;

    public AchievementResponseV2(int achievementId, String name, String info, Type type, int xp, boolean finished, TypeSuperclass typeClass){
        this.achievementId = achievementId;
        this.name = name;
        this.info = info;
        this.type = type;
        this.xp = xp;
        this.finished = finished;
        setTypeData(typeClass);
    }
    public AchievementResponseV2(int achievementId, String name, String info, Type type, int xp, boolean finished){
        this.achievementId = achievementId;
        this.name = name;
        this.info = info;
        this.type = type;
        this.xp = xp;
        this.finished = finished;
    }

    public AchievementResponseV2 toResponse(Achievement achievement, TypeSuperclass typeClass){
        return new AchievementResponseV2(
                achievement.getAchievementId(),
                achievement.getName(),
                achievement.getInfo(),
                achievement.getType(),
                achievement.getXp(),
                achievement.isFinished(),
                typeClass
                );
    }

    public void setTypeData(TypeSuperclass typeClass){
        if(typeClass.getClass().equals(AmountAchievement.class)){
            this.unit = ((AmountAchievement) typeClass).getUnit();
        }else if(typeClass.getClass().equals(GoalAchievement.class)){
            this.deadline = ((GoalAchievement) typeClass).getDeadline();
        }else if(typeClass.getClass().equals(DailyAchievement.class)){
            this.currentStreak = ((DailyAchievement) typeClass).getCurrentStreak();
            this.biggestStreak = ((DailyAchievement) typeClass).getBiggestStreak();
        }else{
            throw new ActionNotAllowed("Type must be set to one of the values");
        }
    }
}
