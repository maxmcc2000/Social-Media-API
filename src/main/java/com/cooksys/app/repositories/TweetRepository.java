package com.cooksys.app.repositories;

import com.cooksys.app.entities.Tweet;
import com.cooksys.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByDeletedFalse();

    //find all without deleted reposts
    @Query("SELECT t FROM Tweet t WHERE t.repostOf = :tweet AND t.deleted = false")
    List<Tweet> findAllByRepostOfIsTweetAndNotDeleted(Tweet tweet);


    Optional<Tweet> findById(Long id);
    
    @Query("SELECT t FROM Tweet t WHERE :user MEMBER OF t.mentionedUsers AND t.deleted = false")
    List<Tweet> findTweetsByMentionedUsersAndNotDeleted(User user);
    

}
