package com.cooksys.app.controllers;

import com.cooksys.app.servies.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;

}
