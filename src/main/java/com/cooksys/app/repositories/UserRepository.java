package com.cooksys.app.repositories;

import com.cooksys.app.entities.Credentials;
import com.cooksys.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByCredentials(Credentials credentials);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

}
