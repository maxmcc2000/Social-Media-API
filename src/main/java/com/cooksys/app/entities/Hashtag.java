package com.cooksys.app.entities;

import java.time.LocalDateTime;
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
    private LocalDateTime firstUsed;
    
    @Column(nullable = false)
    private LocalDateTime lastUsed;
    
    @ManyToMany(mappedBy = "hashtags") //We use this field to allow mapping between hashtags and tweets in a many to many relationship
    private List<Tweet> tweets = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() { //When first uploading to the DB, we make sure to
    	//Set the timestamps, and format the label to be case insensitive.
    	firstUsed = LocalDateTime.now();
    	lastUsed = firstUsed;
    	if (label != null) {
            label = label.toLowerCase();
        }
    }
    
//    @PostLoad //lastUsed actually needs to be updated every time a new tweet is tagged with the hashtag
    			//This means our service should update lastUsed, instead of here
//    protected void onLoad() {
//    	lastUsed = LocalDateTime.now();
//    }

    @PreUpdate
    private void formatLabel() { //Making our labels case insensitive when updating hashtags
        if (label != null) {
            label = label.toLowerCase();
        }
    }
}