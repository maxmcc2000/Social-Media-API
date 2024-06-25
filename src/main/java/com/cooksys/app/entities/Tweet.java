package com.cooksys.app.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(nullable = false)
    private Timestamp posted;

    private String content;
    private Tweet inReplyTo;
    private Tweet repostOf;
    
    @ManyToMany
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
        )
    private List<Hashtag> hashtags = new ArrayList<>(); //New field to relate with hashtags in a many to many relationship
    
    public Tweet(Long id, User author, Timestamp posted, String content) {
        this.id = id;
        this.author = author;
        this.posted = posted;
        this.content = content;
    }

    public Tweet(Long id, User author, Timestamp posted, Tweet repostOf) {
        this.id = id;
        this.author = author;
        this.posted = posted;
        this.repostOf = repostOf;
    }

    public Tweet(Long id, User author, Timestamp posted, String content, Tweet inReplyTo) {
        this.id = id;
        this.author = author;
        this.posted = posted;
        this.content = content;
        this.inReplyTo = inReplyTo;
    }
}
