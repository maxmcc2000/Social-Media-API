package com.cooksys.app.services;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.TweetResponseDto;
import com.cooksys.app.dtos.UserRequestDto;
import com.cooksys.app.dtos.UserResponseDto;
import com.cooksys.app.entities.User;

import java.util.List;

public interface UserService {

	
	UserResponseDto getUser(String username);
	
	UserResponseDto setUser(String username, UserRequestDto u);
	
	UserResponseDto softDelete(CredentialsDto c, String username);
	
    void followUser(CredentialsDto credentials, String username);

    List<UserResponseDto> getFollowers(String username);

    void unfollowUser(CredentialsDto credentialsDto, String username);

    List<UserResponseDto> getAllUsers();

    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<TweetResponseDto> getUserTweets(String username);
    
    List<TweetResponseDto> getFeed(String username);
    
	List<TweetResponseDto> getMen(String username);
	
    List<UserResponseDto> getFollowing(String username);
   
}
