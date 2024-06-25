package com.cooksys.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "followers_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<User> following;

    @Column(nullable = false)
    boolean deleted;

}
