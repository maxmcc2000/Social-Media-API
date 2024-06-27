package com.cooksys.app.controllers;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.TweetRequestDto;
import com.cooksys.app.dtos.ContextDto;
import com.cooksys.app.dtos.UserResponseDto;
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

    @GetMapping("/{id}/context")
    public ContextDto getContext(@PathVariable long id){
    	return tweetService.getContext(id);
	}

    @GetMapping("/{id}")
    public TweetResponseDto retrieveTweetById(@PathVariable Long id) {
        return tweetService.retrieveTweetById(id);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getReplies(@PathVariable Long id){
    	return tweetService.getReplies(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getReposts(@PathVariable Long id){
    	return getReposts(id);
    }

    @GetMapping("{id}/mentions")
    public List<UserResponseDto> getMentions(@PathVariable Long id){
    	return getMentions(id);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweetById(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.deleteTweetById(id, credentialsDto);
    }
    @PostMapping("/{id}/like")
    public void likeTweet(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
        tweetService.likeTweet(credentialsDto, id);
    }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getLikedTweetUsers(@PathVariable("id") Long id) {
        return tweetService.getLikedTweetUsers(id);
    }
}
