package com.miroslav.acitivity_tracker.group.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.user.model.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "_group")
public class Group {

    @Id
    @GeneratedValue
    private Integer groupId;
    private String name;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "_group_activities",
            joinColumns = {@JoinColumn(name = "groupId", referencedColumnName = "groupId")},
            inverseJoinColumns = {@JoinColumn(name = "activityId", referencedColumnName = "activityId")}
    )
    private List<Activity> activities;
    @ManyToOne
    @JoinColumn(name = "profileId", referencedColumnName = "profileId")
    private Profile profile;
}
