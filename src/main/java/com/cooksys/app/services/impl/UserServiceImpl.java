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
import com.cooksys.app.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;
    private final ProfileMapper profileMapper;
    private final TweetMapper tweetMapper;
    
    
    
    @Override
    public User getUser(String username) {
    	
    	User u = userRepository.findByCredentialsUsername(username);
    	
    	if(u == null)
    		throw new NotFoundException("User not found");

    	return u;
    }
    
    public User setUser(UserRequestDto u, String username) {
    	    	
    	if(u == null || username == null)
    		throw new BadRequestException("Null user lookup");
    	
    	User userFound = userRepository.findByCredentialsUsername(username);
    	
    	if (userFound == null)
    		throw new NotFoundException("User not found");
    	
    	if (!userFound.getCredentials().equals(credentialsMapper.DtoToEntity(u.getCredentialsDto())))
    		throw new NotAuthorizedException("Invalid password");
    	else {
    		
    		userFound.setProfile(profileMapper.DtoToEntity(u.getProfileDto()));
    		userRepository.save(userFound);
    		return userFound;     		
    	}
    }
    
    
    public User softDelete(CredentialsDto c) {
    	
    	if(c == null) {
    		throw new BadRequestException("Null credentials lookup");
    	}
    	
    	User u = userRepository.findByCredentials(credentialsMapper.DtoToEntity(c));

    	if(u == null)
    		throw new NotAuthorizedException("Unauthorized username or password");
    	
    	
    	//deletes user
    	u.setDeleted(true);
    	
    	
    	//deletes all tweets use is author of
    	for(Tweet t : u.getTweets()) {
    		t.setDeleted(true);
    	}
    	
    	//should we also delete their mentions and likes too...? 
    	//if so, we must iterate and search other tables
    	
    	return u;
    	
        }
    
    


    @Override
    public void followUser(CredentialsDto credentialsDto, String username) {

        if (!userRepository.existsByCredentials(credentialsMapper.DtoToEntity(credentialsDto))) {

            throw new NotAuthorizedException("Invalid username or password.");

        } User user = userRepository.findByCredentials(credentialsMapper.DtoToEntity(credentialsDto));

        if (user.getFollowing().stream().findFirst().filter(e -> e.getCredentials().getUsername().equals(username)).isPresent()) {

            throw new BadRequestException("User is already followed.");

        } if (!userRepository.existsByCredentialsUsername(username)) {

            throw new NotFoundException("User not found.");

        } user.getFollowing().add(userRepository.findByCredentialsUsername(username));
        userRepository.saveAndFlush(user);

    }

    @Override
    public List<User> getFollowers(String username) {

        return userRepository.findByCredentialsUsername(username).getFollowing().stream().filter(e -> !e.isDeleted()).collect(Collectors.toList());

    }

    @Override
    public void unfollowUser(CredentialsDto credentialsDto, String username) {

        if (!userRepository.existsByCredentials(credentialsMapper.DtoToEntity(credentialsDto))) {

            throw new NotAuthorizedException("Invalid username or password.");

        } User user = userRepository.findByCredentials(credentialsMapper.DtoToEntity(credentialsDto));

        if (user.getFollowing().stream().findFirst().filter(e -> e.getCredentials().getUsername().equals(username)).isEmpty()) {

            throw new BadRequestException("User is not followed.");

        } if (!userRepository.existsByCredentialsUsername(username)) {

            throw new NotFoundException("User not found.");

        } user.getFollowing().remove(userRepository.findByCredentialsUsername(username));
        userRepository.saveAndFlush(user);

    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> notDeleted = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            //System.out.println(user.getProfile());
            if (!user.isDeleted()) notDeleted.add(user);
        }
        //System.out.println(notDeleted.get(0).toString());
        return userMapper.entitiesToDtos(notDeleted);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User newUser = userMapper.DtoToEntity(userRequestDto);
        Credentials credentials = credentialsMapper.DtoToEntity(userRequestDto.getCredentialsDto());
        Profile profile = profileMapper.DtoToEntity(userRequestDto.getProfileDto());
        newUser.setCredentials(credentials);
        newUser.setProfile(profile);
//        System.out.println(userRequestDto);
//        System.out.println(newUser.toString());
        //if any required fields are missing
        if (newUser.getCredentials() == null || newUser.getProfile() == null || profile.getEmail() == null) {
            throw new BadRequestException("One or more required fields are missing.");
        }
        //if this username is found in the database
        if (userRepository.findByCredentialsUsername(newUser.getCredentials().getUsername()) != null) {
            User existingUser = userRepository.findByCredentialsUsername(newUser.getCredentials().getUsername());
            //if the given credentials match a deleted user
            if (existingUser.isDeleted() && existingUser.getCredentials().equals(newUser.getCredentials())){
                existingUser.setDeleted(false);
                return userMapper.entityToDto(existingUser);
            } else {
                //username already taken
                throw new NotAuthorizedException("Username is already taken.");
            }
        } else {
            newUser.setDeleted(false);
            return userMapper.entityToDto(userRepository.saveAndFlush(newUser));
        }
    }

    @Override
    public List<TweetResponseDto> getUserTweets(String username) {
        User user = userRepository.findByCredentialsUsername(username);
        List<Tweet> nonDeletedTweets = new ArrayList<>();
        for (Tweet tweet : user.getTweets()) {
            if (!tweet.isDeleted()) {
                nonDeletedTweets.add(tweet);
            }
        }
        return tweetMapper.entitiesToDtos(nonDeletedTweets);
    }

}
