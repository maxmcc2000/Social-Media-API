package com.cooksys.app.controllers;

import com.cooksys.app.dtos.*;
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
    	return tweetService.getReposts(id);
    }

    @GetMapping("{id}/mentions")
    public List<UserResponseDto> getMentions(@PathVariable Long id){
    	return tweetService.getMentions(id);
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

    @PostMapping("/{id}/repost")
    public TweetResponseDto createRepost(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.createRepost(id, credentialsDto);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagResponseDto> getTweetHashtags(@PathVariable Long id) {
        return tweetService.getTweetHashtags(id);
    }

    @PostMapping("/{id}/reply")
    public TweetResponseDto replyToTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.replyToTweet(id, tweetRequestDto);
    }
}
