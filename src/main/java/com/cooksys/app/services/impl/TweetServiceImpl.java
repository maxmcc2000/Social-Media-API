package com.cooksys.app.services.impl;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.TweetRequestDto;
import com.cooksys.app.dtos.TweetResponseDto;
import com.cooksys.app.entities.Credentials;
import com.cooksys.app.entities.Hashtag;
import com.cooksys.app.entities.Tweet;
import com.cooksys.app.entities.User;
import com.cooksys.app.exceptions.BadRequestException;
import com.cooksys.app.exceptions.NotAuthorizedException;
import com.cooksys.app.exceptions.NotFoundException;
import com.cooksys.app.mapper.CredentialsMapper;
import com.cooksys.app.mapper.TweetMapper;
import com.cooksys.app.repositories.HashtagRepository;
import com.cooksys.app.repositories.TweetRepository;
import com.cooksys.app.repositories.UserRepository;
import com.cooksys.app.services.HashtagService;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final CredentialsMapper credentialsMapper;

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
        List<User> tempList = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group().replace("@", "");
            if (userRepository.existsByCredentialsUsername(match)) {
                tempList.add(userRepository.findByCredentialsUsername(match));
            } tweet.setMentionedUsers(tempList);
        }

        //same thing but for hashTags
        matcher = Pattern.compile("#\\w+").matcher(tweet.getContent());
        List<Hashtag> tempHashtagList = new ArrayList<Hashtag>();
        while (matcher.find()) {
            String match = matcher.group().replace("#", "");
            Hashtag hashtag;
            if (hashtagRepository.existsByLabel(match)) {
                hashtag = hashtagRepository.findByLabel(match).get();
                hashtag.setLastUsed(Timestamp.valueOf(LocalDateTime.now()));
            } else {
                hashtag = hashtagService.createHashtag(match);
            } tempHashtagList.add(hashtagRepository.saveAndFlush(hashtag));
        } tweet.setHashtags(tempHashtagList);

        return tweetMapper.entityTodto(tweetRepository.saveAndFlush(tweet));

    }

    @Override
    public List<TweetResponseDto> retrieveAllTweets() {

        List<TweetResponseDto> tweetResponseDtos = tweetMapper.entitiesToResponseDtos(tweetRepository.findByDeletedFalse());
        //Sort in reverse-chronological order
        tweetResponseDtos.sort((o1, o2) -> o2.getPosted().compareTo(o1.getPosted()));
        return tweetResponseDtos;

    }

    @Override
    public TweetResponseDto retrieveTweetById(Long id) {

        Optional<Tweet> tweet = tweetRepository.findById(id);

        if (tweet.isEmpty()) {

            throw new NotFoundException("Tweet does not exist yet.");

        } if (tweet.get().isDeleted()) {

            throw new NotFoundException("Tweet does not exist yet.");

        } return tweetMapper.entityTodto(tweet.get());

    }

    @Override
    public TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsDto) {

        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty()) {

            throw new NotFoundException("Tweet does not exist yet.");

        } Tweet tweetToDeleted = tweet.get();
        Credentials credentials = tweetToDeleted.getAuthor().getCredentials();
        Credentials credentials1 = credentialsMapper.DtoToEntity(credentialsDto);
        //credential object not assigning HashCode() correctly, .equals not working. Used .getUsername.equals instead for the time being
        if (!credentials.equals(credentials1)) {

            throw new NotAuthorizedException("You cannot delete a Tweet that you didn't make.");

        } tweetToDeleted.setDeleted(true);
        return tweetMapper.entityTodto(tweetToDeleted);

    }

}
