package com.cooksys.app.services.impl;

import com.cooksys.app.dtos.TweetRequestDto;
import com.cooksys.app.dtos.TweetResponseDto;
import com.cooksys.app.entities.Hashtag;
import com.cooksys.app.entities.Tweet;
import com.cooksys.app.exceptions.BadRequestException;
import com.cooksys.app.exceptions.NotAuthorizedException;
import com.cooksys.app.mapper.TweetMapper;
import com.cooksys.app.repositories.HashtagRepository;
import com.cooksys.app.repositories.TweetRepository;
import com.cooksys.app.repositories.UserRepository;
import com.cooksys.app.services.HashtagService;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.cooksys.app.services.TweetService;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{

    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final HashtagService hashtagService;

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {

        if (!userRepository.existsByCredentialsUsername(tweetRequestDto.getCredentials().getUsername())) {

            throw new NotAuthorizedException("Invalid username or password");

        } Tweet tweet = tweetMapper.requestDtoToEntity(tweetRequestDto);
        tweet.setPosted(Timestamp.valueOf(LocalDateTime.now()));

        if (tweet.getContent() == null || tweet.getContent().isEmpty()) {

            throw new BadRequestException("Tweet must contain content.");

        } tweet.setAuthor(userRepository.findByCredentialsUsername(tweetRequestDto.getCredentials().getUsername()));

        //creates a pattern of "@followed by text" and find each instance in our content, add user to tweet's mentionedUsers
        Matcher matcher = Pattern.compile("@\\w+").matcher(tweet.getContent());
        while (matcher.find()) {
            if (userRepository.existsByCredentialsUsername(matcher.group().replace("@", ""))) {
                tweet.getMentionedUsers().add(userRepository.findByCredentialsUsername(matcher.group().replace("@", "")));
            }
        }

        //same thing but for hashTags
        matcher = Pattern.compile("#\\w+").matcher(tweet.getContent());
        while (matcher.find()) {
            String match = matcher.group().replace("#", "");
            Hashtag hashtag;
            if (hashtagRepository.existsByLabel(match)) {
                hashtag = hashtagRepository.findByLabel(match);
                hashtag.setLastUsed(Timestamp.valueOf(LocalDateTime.now()));
            } else {
                hashtag = hashtagService.createHashtag(match);
            } tweet.getHashtags().add(hashtagRepository.saveAndFlush(hashtag));
        }

        return tweetMapper.entityTodto(tweetRepository.saveAndFlush(tweet));

    }
}
