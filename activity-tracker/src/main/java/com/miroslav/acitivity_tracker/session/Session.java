package com.miroslav.acitivity_tracker.session;


import com.miroslav.acitivity_tracker.activity.model.Activity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
@EntityListeners(AuditingEntityListener.class)
public class Session {

    @Id
    @GeneratedValue
    private Integer sessionId;
    private String name;
    private String info;
    private ArrayList<String> notes;
    private LocalDateTime start;
    private LocalDateTime finish;
    private Timestamp duration;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(updatable = false, nullable = false)
    private Date updatedAt;

//    @ManyToOne
//    @JoinColumn(name = "activityId")
//    private Activity activity;
}
