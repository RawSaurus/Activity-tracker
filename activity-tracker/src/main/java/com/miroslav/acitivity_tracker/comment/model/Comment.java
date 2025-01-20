package com.miroslav.acitivity_tracker.comment.model;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_comment")
public class Comment {

    //TODO build comment service, dtos, controller
    @Id
    @GeneratedValue
    private Integer commentId;
    private String info;
    private int likes;
    @CreatedBy
    @Column(updatable = false, nullable = false)
    private Integer createdBy;
    @CreatedDate
    @Column(nullable = false)
    private Date postedAt;
    //TODO add connection to activity, profile

//    @ManyToOne
//    @JoinColumn(name = "activityId")
//    private Activity activity;


}
