package com.cooksys.app.entities;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp posted;
    
    private String content;
    
    @ManyToOne
    private Tweet inReplyTo;
    @ManyToOne
    private Tweet repostOf;
    
    @OneToMany
    private List<Tweet> replies; 
    
    @Column(nullable = false)
    private boolean deleted; 
    
    @ManyToMany
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
        )
    private List<Hashtag> hashtags = new ArrayList<>(); //New field to relate with hashtags in a many to many relationship

    @OneToMany /// (mappedBy = "mentions")
    private List<User> mentionedUsers;
    
    
  

}
