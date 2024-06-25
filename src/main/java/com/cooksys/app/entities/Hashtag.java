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
    
    
    
    //run the PrePersist methods here
    @PrePersist
    protected void prepersistMethods() {
    	onCreate();
    	formatLabel();
    }
    
   // @PrePersist
    protected void onCreate() {
    	firstUsed = LocalDateTime.now();
    	lastUsed = firstUsed;
    }
    
    @PostLoad
    protected void onLoad() {
    	lastUsed = LocalDateTime.now();
    }
    
    //@PrePersist
    @PreUpdate
    private void formatLabel() { //Making our labels case insensitive
        if (label != null) {
            label = label.toLowerCase();
        }
    }
}