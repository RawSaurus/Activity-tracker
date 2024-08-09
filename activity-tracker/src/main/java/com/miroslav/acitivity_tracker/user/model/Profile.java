package com.miroslav.acitivity_tracker.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_profile")
public class Profile {

    @Id
    private Integer profileId;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
        fetch = FetchType.LAZY)
    @MapsId("profileId")
    @JoinColumn(name = "profileId", referencedColumnName = "userId")
    @Setter(AccessLevel.NONE)
    private User user;


    @OneToOne
    @JsonBackReference
    private Activity activity;

    @OneToMany(mappedBy = "profile")
    private List<Activity> activities;

}
