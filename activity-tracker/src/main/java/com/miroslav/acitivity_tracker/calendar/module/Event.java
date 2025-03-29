package com.miroslav.acitivity_tracker.calendar.module;

import com.miroslav.acitivity_tracker.user.model.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Integer eventId;
    private String name;
    @Column(name = "_start")
    private LocalDateTime start;
    @Column(name = "_end")
    private LocalDateTime end;
    @Enumerated(value = EnumType.STRING)
    private EventType type;
    private Integer linkId;
    @ManyToOne
    @JoinColumn(name = "profileId", referencedColumnName = "profileId")
    private Profile profile;

}
