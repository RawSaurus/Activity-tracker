package com.miroslav.acitivity_tracker.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
import java.util.Map;

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
//    @ElementCollection
//    private Map<String, List<Integer>> groups;

    @OneToMany(cascade = CascadeType.ALL)//(mappedBy = "profile")//changed from creatorId
    @JoinTable(name = "_profile_activities",
            joinColumns = {@JoinColumn(name = "profileId")},
            inverseJoinColumns = {@JoinColumn(name = "activityId")}
    )
    //TODO untested cascade/ wont compile with MERGE
//    cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonBackReference
    private List<Activity> activities;


}
