package com.miroslav.acitivity_tracker.comment.model;

import com.miroslav.acitivity_tracker.activity.model.Activity;
import com.miroslav.acitivity_tracker.template.model.Template;
import com.miroslav.acitivity_tracker.user.model.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;

import static jakarta.persistence.FetchType.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Integer commentId;
    private String header;
    private String text;
    private int likes;
    @CreatedBy
    @Column(updatable = false, nullable = false)
    private Integer createdBy;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date postedAt;
    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "creatorId", referencedColumnName = "profileId")
    private Profile profile;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "templateId", referencedColumnName = "templateId")
    private Template template;



}
