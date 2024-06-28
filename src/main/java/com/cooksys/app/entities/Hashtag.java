package com.cooksys.app.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(unique=true, nullable=false)
    private String label;
    
    @Column(nullable = false, updatable = false)
    private Timestamp firstUsed;
    
    @Column(nullable = false)
    private Timestamp lastUsed;
    
    @ManyToMany(mappedBy = "hashtags") //We use this field to allow mapping between hashtags and tweets in a many to many relationship
    private List<Tweet> tweets = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() { //When first uploading to the DB, we make sure to
    	//Set the timestamps, and format the label to be case insensitive.
    	Instant now = Instant.now();
    	firstUsed = Timestamp.from(now);
    	lastUsed = firstUsed;
    }

}