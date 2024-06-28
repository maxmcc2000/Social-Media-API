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
    public UserResponseDto getUser(@PathVariable String username) {
    	return userService.getUser(username);
    }
    
    @PatchMapping("/@{username}")
    public UserResponseDto setUser(@PathVariable String username, @RequestBody UserRequestDto u) {
    	return userService.setUser(username, u);
    }
    
    @PostMapping("/@{username}/follow")
    public void followUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
        userService.followUser(credentialsDto, username);
    }
    


    @GetMapping("/@{username}/following")
    public List<UserResponseDto> getFollowing(@PathVariable String username) {
        return userService.getFollowing(username);
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
    public UserResponseDto softDelete(@RequestBody CredentialsDto c, @PathVariable String username) {
    	return userService.softDelete(c, username);
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
    public List<UserResponseDto> getFollowers(@PathVariable String username){
    	return userService.getFollowers(username);
    }
    
    

}
