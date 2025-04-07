package com.miroslav.acitivity_tracker.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.calendar.module.Event;
import com.miroslav.acitivity_tracker.file.model.File;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
import java.util.Map;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_profile")
public class Profile {

    //TODO clean up
    @Id
    private Integer profileId;
    @OneToOne(//cascade = {CascadeType.MERGE, CascadeType.REFRESH},
             )
//    @MapsId("profileId")
//    @JoinColumn(name = "profileId", referencedColumnName = "userId")
//    @Setter(AccessLevel.NONE)
    private User user;

//    @JoinColumn(name = "username", referencedColumnName = "username")
    private String username;
    @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
    private File picture;
    @OneToMany(cascade = CascadeType.ALL)//(mappedBy = "profile")//changed from creatorId
    @JoinTable(name = "_profile_activities",
            joinColumns = {@JoinColumn(name = "profileId")},
            inverseJoinColumns = {@JoinColumn(name = "activityId")}
    )
    @JsonBackReference
    private List<Activity> activities;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> events;

    //TODO stat properties


}
