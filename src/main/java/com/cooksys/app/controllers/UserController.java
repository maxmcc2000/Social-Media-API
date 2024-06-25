package com.cooksys.app.controllers;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.entities.Credentials;
import com.cooksys.app.servies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/@{username}/follow")
    public void followUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
        userService.followUser(credentialsDto, username);
    }

}
