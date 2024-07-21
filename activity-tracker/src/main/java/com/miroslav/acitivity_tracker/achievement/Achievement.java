package com.miroslav.acitivity_tracker.achievement;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_achievement")
public class Achievement {

    @Id
    @GeneratedValue
    private Integer achievementId;
    private String name;
    private String info;
    private String type; //enum
    private byte[] picture;
    private String typeCheckmark; //enum
    @CreatedDate
    @Column(unique = true,nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(unique = true,nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "activityId")
    private Activity activity;


}
