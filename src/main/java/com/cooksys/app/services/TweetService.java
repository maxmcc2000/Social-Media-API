package com.cooksys.app.services;

import com.cooksys.app.dtos.TweetRequestDto;
import com.cooksys.app.dtos.TweetResponseDto;
import com.cooksys.app.entities.Tweet;

public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

}
