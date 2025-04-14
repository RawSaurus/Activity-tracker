package com.miroslav.acitivity_tracker.file.model;

import com.miroslav.acitivity_tracker.user.model.Profile;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "_file")
public class File {

    @Id
    @GeneratedValue
    private Integer fileId;
    private String name;
    private String fileCode;
    private Long size;
    @ManyToOne(
            cascade = {jakarta.persistence.CascadeType.MERGE, jakarta.persistence.CascadeType.PERSIST, jakarta.persistence.CascadeType.REFRESH},
            fetch = jakarta.persistence.FetchType.LAZY
    )
    @JoinColumn(name = "profileId", referencedColumnName = "profileId")
    private Profile postedBy;

    public String getFilePath(){
        return fileCode + "-" + name;
    }
}
