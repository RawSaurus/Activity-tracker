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
@Builder
@Entity
@Table(name = "_profile")
public class Profile {

    @Id
    private Integer profileId;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH},
        fetch = FetchType.LAZY)
//    @MapsId("profileId")
//    @JoinColumn(name = "profileId", referencedColumnName = "userId")
//    @Setter(AccessLevel.NONE)
    private User user;

    //    @JoinColumn(name = "username", referencedColumnName = "username")
    private String username;

    @OneToMany(mappedBy = "creator")
    @JsonBackReference
    private List<Activity> activities;

}
