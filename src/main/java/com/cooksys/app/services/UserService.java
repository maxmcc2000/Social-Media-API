package com.cooksys.app.services;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.entities.User;

import java.util.List;

public interface UserService {

    void followUser(CredentialsDto credentials, String username);

    List<User> getFollowers(String username);

    void unfollowUser(CredentialsDto credentialsDto, String username);
}
