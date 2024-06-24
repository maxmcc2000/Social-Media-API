package com.cooksys.spring_assessment_social_media_june_2024_team_3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username", nullable = false, insertable = false, updatable = false)
    private String username;

    @CreationTimestamp
    @Column
    private LocalDateTime joined;

    @Embedded
    private Profile profile;

    @Embedded
    private Credentials credentials;

}
