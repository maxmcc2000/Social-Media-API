package com.cooksys.app.services.impl;

import com.cooksys.app.dtos.*;
import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.entities.Credentials;
import com.cooksys.app.entities.Hashtag;
import com.cooksys.app.entities.Tweet;
import com.cooksys.app.entities.User;
import com.cooksys.app.exceptions.BadRequestException;
import com.cooksys.app.exceptions.NotAuthorizedException;
import com.cooksys.app.exceptions.NotFoundException;
import com.cooksys.app.mapper.CredentialsMapper;
import com.cooksys.app.mapper.HashtagMapper;
import com.cooksys.app.mapper.TweetMapper;
import com.cooksys.app.mapper.UserMapper;
import com.cooksys.app.repositories.HashtagRepository;
import com.cooksys.app.repositories.TweetRepository;
import com.cooksys.app.repositories.UserRepository;
import com.cooksys.app.services.HashtagService;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.cooksys.app.services.TweetService;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{

	private final UserMapper usermapper;
    private final TweetMapper tweetMapper;
    private final CredentialsMapper credentialsMapper;
    private final HashtagMapper hashtagMapper;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final HashtagService hashtagService;

    //Helper method to get a Tweet by id
    private Tweet getTweet(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if (optionalTweet.isEmpty()) throw new NotFoundException("Tweet with id " + id + " not found.");
        return optionalTweet.get();
    }

    private User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
        if (optionalUser.isEmpty()) throw new NotFoundException("User " + username + " not found.");

        return optionalUser.get();
    }

    private User getUser(Credentials credentials) {
        Optional<User> optionalUser = userRepository.findByCredentials(credentials);
        if (optionalUser.isEmpty()) throw new NotFoundException("User with credentials " + credentials + " not found.");
        return optionalUser.get();
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {

        if (!userRepository.existsByCredentialsUsername(tweetRequestDto.getCredentials().getUsername())) {

            throw new NotAuthorizedException("Invalid username or password");

        } Tweet tweet = tweetMapper.requestDtoToEntity(tweetRequestDto);
        tweet.setPosted(Timestamp.valueOf(LocalDateTime.now()));

        if (tweet.getContent() == null || tweet.getContent().isEmpty()) {

            throw new BadRequestException("Tweet must contain content.");

        } //tweet.setAuthor(userRepository.findByCredentialsUsername(tweetRequestDto.getCredentials().getUsername()));
        tweet.setAuthor(getUserByUsername(tweetRequestDto.getCredentials().getUsername()));

        //creates a pattern of "@followed by text" and find each instance in our content, add user to tweet's mentionedUsers
        Matcher matcher = Pattern.compile("@\\w+").matcher(tweet.getContent());
        List<User> tempList = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group().replace("@", "");
            if (userRepository.existsByCredentialsUsername(match)) {
                tempList.add(getUserByUsername(match));
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
    public void likeTweet(CredentialsDto credentialsDto, Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);

        if (optionalTweet.isEmpty()) throw new NotFoundException("Tweet with id " + id + " not found.");

        Tweet tweetToLike = optionalTweet.get();
        if (tweetToLike.isDeleted()) throw new BadRequestException("Tweet with id " + id +   " has been deleted.");

        Optional<User> optionalUser = userRepository.findByCredentials(credentialsMapper.DtoToEntity(credentialsDto));

        if (optionalUser.isEmpty()) throw new NotFoundException("User with credentials " + credentialsMapper.DtoToEntity(credentialsDto).toString() + " not found.");

        User user = optionalUser.get();

        user.getLikes().add(tweetToLike);
        userRepository.saveAndFlush(user);
    }

    @Override
    public ContextDto getContext(long id){

    	Optional<Tweet> optional = tweetRepository.findById(id);

    	if(optional.isEmpty())
    		throw new NotFoundException("User not found.");

    	ContextDto context = new ContextDto();

    	Tweet entity = optional.get();
    	context.setTarget(entity);


    	ArrayList<Tweet> before = new ArrayList<Tweet>();
    	ArrayList<Tweet> after = new ArrayList<Tweet>();

    	Tweet prevTweet = entity.getInReplyTo();

    	while(prevTweet != null) {
    		if(!prevTweet.isDeleted())
    			before.add(prevTweet);
    	}

    	//puts  list in chronological order
    	Collections.reverse(before);
    	context.setBefore(before);


    	for(Tweet t : entity.getReplies()) {
    		replyHelper(after, t);
    	}


    	after.sort(Comparator.comparing(Tweet::getPosted));
    	context.setAfter(after);

    	return context;
    }

    	//assume two tweets can never reply to one another
    	public void replyHelper(ArrayList<Tweet> after, Tweet replyTweet){

    		if(replyTweet == null) {
    			if(!replyTweet.isDeleted()) {
    				after.add(replyTweet);
    			}
    		}

    		for(Tweet t : replyTweet.getReplies()) {

    			if(!replyTweet.isDeleted()) {
    				after.add(replyTweet);
    			}
    			 replyHelper(after, t);
    		}

    	}



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

//        Optional<Tweet> tweet = tweetRepository.findById(id);
//        if (tweet.isEmpty()) {
//
//            throw new NotFoundException("Tweet does not exist yet.");
//
//        } Tweet tweetToDeleted = tweet.get();

        Tweet tweetToDeleted = getTweet(id);

        Credentials credentials = tweetToDeleted.getAuthor().getCredentials();
        Credentials credentials1 = credentialsMapper.DtoToEntity(credentialsDto);
        //credential object not assigning HashCode() correctly, .equals not working. Used .getUsername.equals instead for the time being
        if (!credentials.equals(credentials1)) {

            throw new NotAuthorizedException("You cannot delete a Tweet that you didn't make.");

        } tweetToDeleted.setDeleted(true);
        return tweetMapper.entityTodto(tweetToDeleted);

    }

    @Override
    public List<TweetResponseDto> getReplies(@PathVariable Long id){
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty() || tweet.get().isDeleted())
            throw new NotFoundException("Tweet does not exist yet.");

        return tweetMapper.entitiesToResponseDtos(tweet.get().getReplies());

    }


    @Override
    public List<TweetResponseDto> getReposts(@PathVariable Long id){

        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty() || tweet.get().isDeleted())
            throw new NotFoundException("Tweet does not exist.");

        return tweetMapper.entitiesToResponseDtos(tweetRepository.findAllByRepostOfIsTweetAndNotDeleted(tweet.get()));

    }

    @Override
    public List<UserResponseDto> getMentions(@PathVariable Long id){

        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty() || tweet.get().isDeleted())
            throw new NotFoundException("Tweet does not exist.");

    	return usermapper.entitiesToDtos(tweet.get().mentionNotDeleted());

    }

    @Override
    public List<UserResponseDto> getLikedTweetUsers(Long id) {

//        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
//        if (optionalTweet.isEmpty()) throw new NotFoundException("Tweet with id " + id + " not found.");
        Tweet tweet = getTweet(id);
        if (tweet.isDeleted()) throw new NotFoundException("Tweet with id " + id + " has been deleted");

        //Query written in UserRepository that finds all Users which contain a tweet with this id
        // in their likes
        List<User> users = userRepository.findUsersByLikedTweet(id);
        return usermapper.entitiesToDtos(users);
    }

    @Override
    public TweetResponseDto createRepost(Long id, CredentialsDto credentialsDto) {
        //get original tweet
        Tweet originalTweet = getTweet(id);
        //create new tweet
        Tweet newTweet = new Tweet();
        // set repost of to original tweet
        newTweet.setRepostOf(originalTweet);
        newTweet.setDeleted(false);

        User user = getUser(credentialsMapper.DtoToEntity(credentialsDto));
        //Update User's tweets
        user.getTweets().add(newTweet);
        //set author of tweet to user
        newTweet.setAuthor(user);
        userRepository.saveAndFlush(user);

        return tweetMapper.entityTodto(newTweet);
    }

    @Override
    public List<HashtagResponseDto> getTweetHashtags(Long id) {
        Tweet tweet = getTweet(id);
        if (tweet.isDeleted()) throw new NotFoundException("Tweet with id " + id + " has been deleted.");
        List<Hashtag> tags = tweet.getHashtags();
        return hashtagMapper.entitiesToDtos(tags);
    }

    @Override
    public TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto) {

        Tweet tweetToReplyTo = getTweet(id);

        if (tweetToReplyTo.isDeleted()) throw new NotFoundException("Tweet with id " + id + " has been deleted.");

        Credentials credentials = credentialsMapper.DtoToEntity(tweetRequestDto.getCredentials());
        User replyUser = getUser(credentials);

        if (replyUser.isDeleted()) throw new NotFoundException("User with credentials " + credentials.toString() + " has been deleted.");
        if (tweetRequestDto.getContent() == null) throw new BadRequestException("The reply has no content.");

        Tweet reply = tweetMapper.requestDtoToEntity(tweetRequestDto);
        reply.setDeleted(false);
        reply.setAuthor(replyUser);
        tweetToReplyTo.getReplies().add(reply);
        reply.setInReplyTo(tweetToReplyTo);

        return tweetMapper.entityTodto(reply);
    }
}
