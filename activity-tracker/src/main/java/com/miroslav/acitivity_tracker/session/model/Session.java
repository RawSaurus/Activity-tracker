package com.miroslav.acitivity_tracker.session.model;


import com.miroslav.acitivity_tracker.activity.model.Activity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "notes", joinColumns = @JoinColumn(name = "sessionId"))
    @Column(name = "notes", nullable = false)
    private List<String> notes = new ArrayList<>();
    private LocalDateTime start;
    private LocalDateTime finish;
    private Timestamp duration;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "_activity_sessions",
            joinColumns = {@JoinColumn(name = "sessionId")},
            inverseJoinColumns = {@JoinColumn(name = "activityId")}
    )
    private Activity activity;
}
