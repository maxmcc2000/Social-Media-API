package com.cooksys.app.repositories;

import com.cooksys.app.entities.Tweet;
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
    List<Tweet> findAllByRepostOfIsTweetAndNotDeleted(@Param("tweet") Tweet tweet);


}
