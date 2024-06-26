package com.cooksys.app.services;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.UserRequestDto;
import com.cooksys.app.dtos.UserResponseDto;
import com.cooksys.app.entities.User;

import java.util.List;

public interface UserService {

	
	User getUser(String username);
	
	User setUser(UserRequestDto u, String username);
	
	User softDelete(CredentialsDto c);
	
    void followUser(CredentialsDto credentials, String username);

    List<User> getFollowers(String username);

    void unfollowUser(CredentialsDto credentialsDto, String username);

    List<UserResponseDto> getAllUsers();

    UserResponseDto createUser(UserRequestDto userRequestDto);
}
