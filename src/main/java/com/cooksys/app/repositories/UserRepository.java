package com.cooksys.app.repositories;

import com.cooksys.app.entities.Credentials;
import com.cooksys.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
    //User findByCredentials(Credentials credentials);

    Optional<User> findByCredentials(Credentials credentials);

    Optional<User> findByCredentialsUsername(String username);

    boolean existsByCredentialsUsername(String username);

    boolean existsByCredentials(Credentials credentials);

    @Query("SELECT u FROM User u JOIN u.likes t WHERE t.id = :tweetId")
    List<User> findUsersByLikedTweet(@Param("tweetId") Long tweetId);

}
