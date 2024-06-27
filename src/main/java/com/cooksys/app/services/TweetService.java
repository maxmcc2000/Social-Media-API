package com.cooksys.app.services;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.TweetRequestDto;
import com.cooksys.app.dtos.ContextDto;
import com.cooksys.app.dtos.TweetResponseDto;
import com.cooksys.app.dtos.UserResponseDto;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> retrieveAllTweets();

    void likeTweet(CredentialsDto credentialsDto, Long id);

    ContextDto getContext(long id);

    List<TweetResponseDto> getReplies(@PathVariable Long id);

    TweetResponseDto retrieveTweetById(Long id);

    TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsDto);

    List<TweetResponseDto> getReposts(@PathVariable Long id);

    List<UserResponseDto> getMentions(@PathVariable Long id);


    List<UserResponseDto> getLikedTweetUsers(Long id);
}
