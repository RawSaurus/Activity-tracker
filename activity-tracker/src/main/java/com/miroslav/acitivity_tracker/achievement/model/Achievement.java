package com.miroslav.acitivity_tracker.achievement.model;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_achievement")
@EntityListeners(AuditingEntityListener.class)
public class Achievement {

    @Id
    @GeneratedValue
    private Integer achievementId;
    private String name;
    private String info;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    private byte[] picture;
    //TODO make an enum
    private String typeCheckmark; //enum
    @CreatedDate
    @Column(updatable = false,nullable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "_activity_achievements",
            joinColumns = {@JoinColumn(name = "achievementId")},
            inverseJoinColumns = {@JoinColumn(name = "activityId")}
    )
    private Activity activity;
}
