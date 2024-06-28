package com.cooksys.app.controllers;
import com.cooksys.app.services.ValidateService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.app.services.ValidateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private final ValidateService validateService;

    @GetMapping("/username/available/@{username}")
    public boolean isUsernameAvailable(@PathVariable String username) {
        return  validateService.isUsernameAvailable(username);
    }

    @GetMapping("/username/exists/@{username}")
    public boolean doesUsernameExist(@PathVariable String username) {
        return validateService.doesUsernameExist(username);
    }

    @GetMapping("/tag/exists/{label}")
    public boolean doesHashtagExist(@PathVariable String label) {
        return validateService.doesHashtagExist(label);
    }
}
