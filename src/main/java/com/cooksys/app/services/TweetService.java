package com.cooksys.app.services;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.TweetRequestDto;
import com.cooksys.app.dtos.TweetResponseDto;

import java.util.List;

public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> retrieveAllTweets();

    TweetResponseDto retrieveTweetById(Long id);

    TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsDto);
}
