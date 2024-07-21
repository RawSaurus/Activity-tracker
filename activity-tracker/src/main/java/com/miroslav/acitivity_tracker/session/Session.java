package com.miroslav.acitivity_tracker.session;


import com.miroslav.acitivity_tracker.activity.model.Activity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_session")
public class Session {

    @Id
    @GeneratedValue
    private Integer sessionId;
    private String name;
    private String info;
    private ArrayList<String> notes;
    private LocalTime duration;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(updatable = false, nullable = false)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "activityId")
    private Activity activity;
}
