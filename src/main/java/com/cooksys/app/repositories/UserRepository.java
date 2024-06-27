package com.cooksys.app.repositories;

import com.cooksys.app.entities.Credentials;
import com.cooksys.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
    //User findByCredentials(Credentials credentials);

    Optional<User> findByCredentials(Credentials credentials);

    User findByCredentialsUsername(String username);

    boolean existsByCredentialsUsername(String username);

    boolean existsByCredentials(Credentials credentials);

}
