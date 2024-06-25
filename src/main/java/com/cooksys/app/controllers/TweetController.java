package com.cooksys.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.app.services.TweetService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;

}
