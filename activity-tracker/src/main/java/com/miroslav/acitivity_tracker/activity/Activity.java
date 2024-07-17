package com.miroslav.acitivity_tracker.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Activity { //TODO distribute field between child and parent class

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer activityId;
    @Column(unique = true)
    private String name;
    private String info;
    private String type;
    private String category; //TODO create entity Category
    private byte[] picture;
    private double rating;
    private int downloads;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(updatable = false, nullable = false)
    private Date updatedAt;

//TODO progress with flyway init
}