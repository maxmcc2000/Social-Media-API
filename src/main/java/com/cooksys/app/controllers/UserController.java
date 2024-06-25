package com.cooksys.app.controllers;

import lombok.RequiredArgsConstructor;
import com.cooksys.app.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user") //shouldn't this be "/users" to match the endpoint documentation in README? -Max
public class UserController {

}
