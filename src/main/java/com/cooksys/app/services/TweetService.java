package com.cooksys.app.services;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.TweetRequestDto;
import com.cooksys.app.dtos.ContextDto;
import com.cooksys.app.dtos.TweetResponseDto;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> retrieveAllTweets();
    
    ContextDto getContext(long id);

    TweetResponseDto retrieveTweetById(Long id);

    TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsDto);
}
