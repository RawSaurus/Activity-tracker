package com.miroslav.acitivity_tracker.activity;

import com.miroslav.acitivity_tracker.achievement.Achievement;
import com.miroslav.acitivity_tracker.comment.Comment;
import com.miroslav.acitivity_tracker.session.Session;
import com.miroslav.acitivity_tracker.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_activity")
public class OriginalActivity {

    @Id
    @GeneratedValue
    private Integer activityId;
    @Column(unique = true)
    private String name;
    private String info;
    private String type;
    private String category; //enum ?
    private byte[] picture;
    private double rating;
    private int downloads;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(updatable = false, nullable = false)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    @OneToMany(mappedBy = "originalActivity")
    private List<Achievement> achievements;
    @OneToMany(mappedBy = "originalActivity")
    private List<Session> sessions;
    @OneToMany(mappedBy = "originalActivity")
    private List<Comment> comments;
}
