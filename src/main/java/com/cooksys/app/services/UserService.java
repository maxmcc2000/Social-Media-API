package com.cooksys.app.services;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.TweetResponseDto;
import com.cooksys.app.dtos.UserRequestDto;
import com.cooksys.app.dtos.UserResponseDto;
import com.cooksys.app.entities.Tweet;
import com.cooksys.app.entities.User;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

	
	UserResponseDto getUser(String username);
	
	UserResponseDto setUser(String username, UserRequestDto u);
	
	User softDelete(CredentialsDto c);
	
    void followUser(CredentialsDto credentials, String username);

    List<User> getFollowers(String username);

    void unfollowUser(CredentialsDto credentialsDto, String username);

    List<UserResponseDto> getAllUsers();

    UserResponseDto createUser(UserRequestDto userRequestDto);

    List<TweetResponseDto> getUserTweets(String username);
    
    List<TweetResponseDto> getFeed(String username);
    
	List<TweetResponseDto> getMen(String username);
	
    List<User> getFollowing(String username);
   
}
