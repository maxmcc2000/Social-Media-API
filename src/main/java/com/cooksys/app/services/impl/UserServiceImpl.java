package com.cooksys.app.services.impl;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.dtos.UserRequestDto;
import com.cooksys.app.dtos.UserResponseDto;
import com.cooksys.app.entities.Credentials;
import com.cooksys.app.entities.Profile;
import com.cooksys.app.entities.User;
import com.cooksys.app.exceptions.BadRequestException;
import com.cooksys.app.exceptions.NotAuthorizedException;
import com.cooksys.app.exceptions.NotFoundException;
import com.cooksys.app.mapper.CredentialsMapper;
import com.cooksys.app.mapper.ProfileMapper;
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
    
    
    
    @Override
    public User getUser(String username) {
    	
    	User u = userRepository.findByCredentialsUsername(username);
    	
    	if(u == null)
    		throw new NotFoundException("User not found");

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

}
