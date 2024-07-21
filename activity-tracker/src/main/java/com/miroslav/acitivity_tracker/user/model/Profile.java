package com.miroslav.acitivity_tracker.user.model;

import jakarta.persistence.*;
import lombok.*;

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


}
