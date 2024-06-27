package com.cooksys.app.controllers;

import com.cooksys.app.dtos.TweetRequestDto;
import com.cooksys.app.dtos.ContextDto;
import com.cooksys.app.dtos.TweetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.cooksys.app.services.TweetService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

    @GetMapping
    public List<TweetResponseDto> retrieveAllTweets() {
        return tweetService.retrieveAllTweets();
    }
    
    @GetMapping
    
    public ContextDto getContext(@PathVariable long id){
    	return tweetService.getContext(id);
    }
}
