package com.cooksys.app.controllers;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.TweetResponseDto;
import com.cooksys.app.dtos.UserRequestDto;
import com.cooksys.app.dtos.UserResponseDto;
import com.cooksys.app.entities.Tweet;
import com.cooksys.app.entities.User;
import com.cooksys.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    
    @GetMapping("/@{username}")
    public User getUsername(@PathVariable String username) {
    	return userService.getUser(username);
    }
    
    @PatchMapping("@{username}")
    public User setUsername(@RequestBody UserRequestDto u, @PathVariable String username) {
    	return userService.setUser(u, username);
    }
    
    @PostMapping("/@{username}/follow")
    public void followUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
        userService.followUser(credentialsDto, username);
    }
    


    @GetMapping("/@{username}/following")
    public List<User> getFollowing(@PathVariable String username) {
        return userService.getFollowers(username);
    }

    @PostMapping("/@{username}/unfollow")
    public void unfollowUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
        userService.unfollowUser(credentialsDto, username);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }
    
    @DeleteMapping("/@{username}")
    public User softDelete(@PathVariable CredentialsDto c) {
    	return userService.softDelete(c);
    }

    @GetMapping("/@{username}/tweets")
    public List<TweetResponseDto> getUserTweets(@PathVariable String username) {
        return userService.getUserTweets(username);
    }
    
    @GetMapping("/@{username}/feed") 
    public List<TweetResponseDto> getFeed(@PathVariable String username){
    	return userService.getFeed(username);
    }
    
    @GetMapping("/@{username}/mentions") 
    public List<TweetResponseDto> getMen(@PathVariable String username){
    	return userService.getMen(username);
    }
    
    @GetMapping("@{username}/followers")
    public List<User> getFollowers(@PathVariable String username){
    	return userService.getFollowers(username);
    }
    
    

}
