package com.miroslav.acitivity_tracker.template.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.miroslav.acitivity_tracker.achievement.model.Achievement;
import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.activity.model.Category;
import com.miroslav.acitivity_tracker.comment.model.Comment;
import com.miroslav.acitivity_tracker.session.model.Session;
import com.miroslav.acitivity_tracker.user.model.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_template")
@EntityListeners(AuditingEntityListener.class)
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer templateId;
    @Column(unique = true)
    private String name;
    private String info;
    private String type;
    @Enumerated(value = EnumType.STRING)
    private Category category;
    //TODO make module for handling pictures
    private byte[] picture;
    private double rating;
    private int downloads;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;
    @Column(name = "creator_name")
    private String creator;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(name = "_profile_templates",
//            joinColumns = {@JoinColumn(name = "templateId")},
//            inverseJoinColumns = {@JoinColumn(name = "profileId")}
//    )
    @JoinColumn(name = "profileId", referencedColumnName = "profileId")
    private Profile profile;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = LAZY
    )
    @JoinTable(name = "_template_achievements",
            joinColumns = {@JoinColumn(name = "templateId")},
            inverseJoinColumns = {@JoinColumn(name = "achievementId")}
    )
    @JsonManagedReference
//  cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Achievement> achievements;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = LAZY
    )
    private List<Comment> comments;
}
