package com.cooksys.app.services.impl;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.TweetResponseDto;
import com.cooksys.app.dtos.UserRequestDto;
import com.cooksys.app.dtos.UserResponseDto;
import com.cooksys.app.entities.Credentials;
import com.cooksys.app.entities.Profile;
import com.cooksys.app.entities.User;
import com.cooksys.app.entities.Tweet;
import com.cooksys.app.exceptions.BadRequestException;
import com.cooksys.app.exceptions.NotAuthorizedException;
import com.cooksys.app.exceptions.NotFoundException;
import com.cooksys.app.mapper.CredentialsMapper;
import com.cooksys.app.mapper.ProfileMapper;
import com.cooksys.app.mapper.TweetMapper;
import com.cooksys.app.mapper.UserMapper;
import com.cooksys.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import com.cooksys.app.services.UserService;
import org.springframework.stereotype.Service;
import com.cooksys.app.repositories.TweetRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;
    private final ProfileMapper profileMapper;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    
    private User getUserByCredentials(Credentials credentials) {
        Optional<User> optionalUser = userRepository.findByCredentials(credentials);
        if (optionalUser.isEmpty()) throw new NotFoundException("User with credentials " + credentials + " not found");
        return optionalUser.get();
    }

    private User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
        if (optionalUser.isEmpty()) throw new NotFoundException("User " + username + " not found.");

        return optionalUser.get();
    }

    @Override
    public UserResponseDto getUser(String username) {
    	
    	Optional<User> u = userRepository.findByCredentialsUsername(username);
    	
    	if(u.isEmpty())
    		throw new NotFoundException("User not found");

    	UserResponseDto resp = userMapper.entityToDto(u.get());
    	resp.setUsername(u.get().getCredentials().getUsername());

    	return resp;
    }
    
    public UserResponseDto setUser(String username, UserRequestDto u) {
    	    	
    	if(u == null || username == null || u.getProfileDto() == null)
    		throw new BadRequestException("Null user lookup");
    	
    	Optional<User> userFound = userRepository.findByCredentialsUsername(username);
    	
    	if (userFound.isEmpty() || userFound.get().isDeleted())
    		throw new NotFoundException("User not found");

        User user = userFound.get();

    	if (!user.getCredentials().equals(credentialsMapper.DtoToEntity(u.getCredentialsDto())))
    		throw new NotAuthorizedException("Invalid password");
    	else {

    		if (u.getProfileDto().getEmail() != null) {

                user.setProfile(profileMapper.DtoToEntity(u.getProfileDto()));

            } UserResponseDto userResponseDto = userMapper.entityToDto(userRepository.saveAndFlush(user));
            userResponseDto.setUsername(username);
            return userResponseDto;

    	}

    }
    
    
    public UserResponseDto softDelete(CredentialsDto c, String username) {
    	
    	if (c == null) {
    		throw new BadRequestException("Null credentials lookup");
    	}

        User u = getUserByCredentials(credentialsMapper.DtoToEntity(c));

    	if (u.isDeleted())
    		throw new BadRequestException("User already deleted");

        if (!c.getUsername().equals(username)) {
            throw new BadRequestException("You can only delete your own account.");
        }

    	//deletes user
    	u.setDeleted(true);

    	//deletes all tweets use is author of
    	for(Tweet t : u.getTweets()) {
    		t.setDeleted(true);
            tweetRepository.saveAndFlush(t);
    	}
    	
    	//should we also delete their mentions and likes too...? 
    	//if so, we must iterate and search other tables
    	UserResponseDto userResponseDto = userMapper.entityToDto(userRepository.saveAndFlush(u));
        userResponseDto.setUsername(username);
    	return userResponseDto;
    	
        }
    
    


    @Override
    public void followUser(CredentialsDto credentialsDto, String username) {

        if (!userRepository.existsByCredentials(credentialsMapper.DtoToEntity(credentialsDto))) {

            throw new NotAuthorizedException("Invalid username or password.");

        } //User user = userRepository.findByCredentials(credentialsMapper.DtoToEntity(credentialsDto));
        User user = getUserByCredentials(credentialsMapper.DtoToEntity(credentialsDto));

        if (user.getFollowing().stream().findFirst().filter(e -> e.getCredentials().getUsername().equals(username)).isPresent()) {

            throw new BadRequestException("User is already followed.");

        } if (!userRepository.existsByCredentialsUsername(username)) {

            throw new NotFoundException("User not found.");

        } //user.getFollowing().add(userRepository.findByCredentialsUsername(username));
        user.getFollowing().add(getUserByUsername(username));
        userRepository.saveAndFlush(user);

    }

    @Override
    public List<UserResponseDto> getFollowing(String username) {

        Optional<User> user = userRepository.findByCredentialsUsername(username);
        List<User> follows = new ArrayList<>();

        if (user.isEmpty()) {

            throw new NotFoundException("User not found.");

        } follows = user.get().getFollowing().stream().filter(e -> !e.isDeleted()).toList();
        List<UserResponseDto> responseDtos = new ArrayList<>();

        for(User follow: follows) {

            UserResponseDto userResponseDto = userMapper.entityToDto(follow);
            userResponseDto.setUsername(follow.getCredentials().getUsername());
            responseDtos.add(userResponseDto);

        } return responseDtos;

    }

    @Override
    public void unfollowUser(CredentialsDto credentialsDto, String username) {

        if (!userRepository.existsByCredentials(credentialsMapper.DtoToEntity(credentialsDto))) {

            throw new NotAuthorizedException("Invalid username or password.");

        } User user = getUserByCredentials(credentialsMapper.DtoToEntity(credentialsDto));
        Optional<User> toUnfollow = userRepository.findByCredentialsUsername(username);

        if (toUnfollow.isEmpty()) {

            throw new NotFoundException("User to unfollow not found.");

        } if (!user.getFollowing().contains(toUnfollow.get())) {

            throw new BadRequestException("User is not followed.");

        } user.getFollowing().remove(toUnfollow.get());
        userRepository.saveAndFlush(user);

    }

    @Override
    public List<UserResponseDto> getAllUsers() {

    	List<UserResponseDto> responseDtos  = new ArrayList<>();


    	for (User user : userRepository.findAll()) {
            //System.out.println(user.getProfile());
            if (!user.isDeleted()) {
            	UserResponseDto entityToAdd = userMapper.entityToDto(user);
            	entityToAdd.setUsername(user.getCredentials().getUsername());
            	responseDtos.add(entityToAdd);
            }
        }

        //System.out.println(notDeleted.get(0).toString());
        return responseDtos;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        User newUser = userMapper.DtoToEntity(userRequestDto);
        Credentials credentials = credentialsMapper.DtoToEntity(userRequestDto.getCredentialsDto());
        Profile profile = profileMapper.DtoToEntity(userRequestDto.getProfileDto());
        newUser.setCredentials(credentials);
        newUser.setProfile(profile);

        //if any required fields are missing
        if (newUser.getCredentials() == null || newUser.getCredentials().getUsername() == null || newUser.getCredentials().getPassword() == null || newUser.getProfile() == null || profile.getEmail() == null) {
            throw new BadRequestException("One or more required fields are missing.");
        }

        //if this username is found in the database
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(userRequestDto.getCredentialsDto().getUsername());

        if (optionalUser.isPresent()) {

            //User existingUser = userRepository.findByCredentialsUsername(newUser.getCredentials().getUsername());
            User existingUser = getUserByUsername(newUser.getCredentials().getUsername());

            //if the given credentials match a deleted user
            if (existingUser.isDeleted()){

                existingUser.setDeleted(false);
                UserResponseDto userResponseDto = userMapper.entityToDto(userRepository.saveAndFlush(existingUser));
                userResponseDto.setUsername(existingUser.getCredentials().getUsername());
                return userResponseDto;

            } else {
                //username already taken
                throw new NotAuthorizedException("Username is already taken.");
            }

        }
        else {

            newUser.setDeleted(false);
            UserResponseDto userResponseDto = userMapper.entityToDto(userRepository.saveAndFlush(newUser));
            userResponseDto.setUsername(credentials.getUsername());
            userResponseDto.setJoined(newUser.getJoined());
            userResponseDto.setProfile(newUser.getProfile());
            return userResponseDto;

        }

    }

    @Override
    public List<TweetResponseDto> getUserTweets(String username) {
        User user = getUserByUsername(username);

        if (user.isDeleted()) throw new NotFoundException("User " + username + " has been deleted.");

        List<TweetResponseDto> nonDeletedTweets = new ArrayList<>();
        
        for (Tweet tweet : user.getTweets()) {
            if (!tweet.isDeleted()) {
                TweetResponseDto resp = tweetMapper.entityTodto(tweet);
    			nonDeletedTweets.add(resp);
            }
        }
//        System.out.println("nonDeletedTweets: " + nonDeletedTweets);
//        System.out.println("Tweet response dto: " + tweetMapper.entitiesToResponseDtos(nonDeletedTweets));
        return nonDeletedTweets;

    }
    
    @Override
    public List<TweetResponseDto> getFeed(String username){
    	//User user = userRepository.findByCredentialsUsername(username);
    	User user = getUserByUsername(username);
    	if(user == null || user.isDeleted()) {
            throw new NotFoundException("User not found.");
    	}
    	
    	List<TweetResponseDto> feed = new ArrayList<>();

    	for(Tweet t : user.getTweets()) {
    		if(!t.isDeleted()) {
    			TweetResponseDto resp = tweetMapper.entityTodto(t);
    			feed.add(resp);
    		}
    	
    	}
    	
    	for(User u : user.getFollowing()) {
    		if(!u.isDeleted()) {
    			for(Tweet t : u.getTweets()) {
    				if(!t.isDeleted()) {
    					TweetResponseDto resp = tweetMapper.entityTodto(t);
        				feed.add(resp);
    				}
    			}
    		}
    	}
    	Collections.sort(feed, Comparator.comparing(TweetResponseDto::getPosted).reversed());
    	return feed; 
    }
    
    
    public List<TweetResponseDto> getMen(String username){
    	//User user = userRepository.findByCredentialsUsername(username);
        User user = getUserByUsername(username);
      
    	if(user == null || user.isDeleted()) 
            throw new NotFoundException("User not found.");
    	
    	
   
    	List<TweetResponseDto> mentions = tweetMapper.entitiesToResponseDtos(tweetRepository.findTweetsByMentionedUsersAndNotDeleted(user));
    	
    	
    	Collections.sort(mentions, Comparator.comparing(TweetResponseDto::getPosted).reversed());
    	return mentions; 
    }
    
    public List<UserResponseDto> getFollowers(String username){
    	
    	Optional<User> user = userRepository.findByCredentialsUsername(username);
    	
    	if(user.isEmpty() || user.get().isDeleted()) {
            throw new NotFoundException("User not found.");
    	}
    	
    	List<User> followers = user.get().getFollowers();
        List<UserResponseDto> responseDtos = new ArrayList<>();

        for (User follower: followers) {

            UserResponseDto responseDto = userMapper.entityToDto(follower);
            responseDto.setUsername(follower.getCredentials().getUsername());
            responseDtos.add(responseDto);

        } return responseDtos;

    }
    

}
