package com.miroslav.acitivity_tracker.activity.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.comment.model.Comment;
import com.miroslav.acitivity_tracker.file.model.File;
import com.miroslav.acitivity_tracker.session.model.Session;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.user.model.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_activity")
@EntityListeners(AuditingEntityListener.class)
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer activityId;
    private String name;
    private String info;
    private String type;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
    private File picture;
    private boolean isPrivate;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;
//    private Integer creatorId;
    @Column(name = "creator_name")
    private String creator;
//    private Integer originalActivity;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "_profile_activities",
            joinColumns = {@JoinColumn(name = "activityId")},
            inverseJoinColumns = {@JoinColumn(name = "profileId")}
    )
    private Profile profile;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = LAZY
    )
    @JoinTable(name = "_activity_achievements",
            joinColumns = {@JoinColumn(name = "activityId")},
            inverseJoinColumns = {@JoinColumn(name = "achievementId")}
    )
    @JsonManagedReference
//  cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Achievement> achievements;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = LAZY
    )
    @JoinTable(name = "_activity_sessions",
            joinColumns = {@JoinColumn(name = "activityId")},
            inverseJoinColumns = {@JoinColumn(name = "sessionId")}
    )
    @JsonManagedReference
    private List<Session> sessions;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = LAZY
    )
    private List<Comment> comments;
}