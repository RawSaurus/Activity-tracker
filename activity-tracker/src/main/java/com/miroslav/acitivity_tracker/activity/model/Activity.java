package com.miroslav.acitivity_tracker.activity.model;

import com.miroslav.acitivity_tracker.achievement.Achievement;
import com.miroslav.acitivity_tracker.comment.Comment;
import com.miroslav.acitivity_tracker.session.Session;
import com.miroslav.acitivity_tracker.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

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
    @Column(unique = true)
    private String name;
    private String group;
    private String info;
    private String type;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    private byte[] picture;
    private double rating;
    private int downloads;
    private boolean isOriginal;
    private boolean isPrivate;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(updatable = false, nullable = false)
    private Date updatedAt;

    @OneToOne
    private User creator;
    @OneToOne
    private Activity originalActivity;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @OneToMany(mappedBy = "activity")
    private List<Achievement> achievements;
    @OneToMany(mappedBy = "activity")
    private List<Session> sessions;
    @OneToMany(mappedBy = "activity")
    private List<Comment> comments;

    //process: create activity (created by = creator, isOriginal = true) -> post activity (isPrivate = false)
    //-> download activity(create new activity, set originalActivity, isOriginal = false)
}