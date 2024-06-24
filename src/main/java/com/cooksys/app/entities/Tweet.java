package com.cooksys.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
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
