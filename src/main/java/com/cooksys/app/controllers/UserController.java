package com.cooksys.app.controllers;

import com.cooksys.app.dtos.CredentialsDto;
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

    @PostMapping("/@{username}/follow")
    public void followUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
        userService.followUser(credentialsDto, username);
    }

    @GetMapping("/@{username}/following")
    public List<User> getFollowers(@PathVariable String username) {
        return userService.getFollowers(username);
    }

    @PostMapping("/@{username}/unfollow")
    public void unfollowUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
        userService.unfollowUser(credentialsDto, username);
    }

}
