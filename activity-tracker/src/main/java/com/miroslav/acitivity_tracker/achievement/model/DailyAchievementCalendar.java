package com.miroslav.acitivity_tracker.achievement.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_daily_achievement_calendar")
public class DailyAchievementCalendar {

    @Id
    @GeneratedValue
    private Integer dailyAchievementCalendarId;
    private boolean checkmark;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate day;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "type_achievement_id", referencedColumnName = "typeAchievementId")
    private DailyAchievement dailyAchievement;
}
