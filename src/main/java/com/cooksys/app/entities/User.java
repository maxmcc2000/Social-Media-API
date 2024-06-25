package com.cooksys.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Profile;

import java.sql.Timestamp;
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

    // After deleting username, it causes an error in followUser and unfollowUser methods in UserServiceImpl
//    @Column(name = "username", nullable = false, insertable = false, updatable = false)
//    private String username;

    @CreationTimestamp
    @Column
    private Timestamp joined;

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

    @ManyToMany
    @JoinTable(
            name = "user_mentions",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Tweet> mentions;

    @Column(nullable = false)
    boolean deleted;

}
