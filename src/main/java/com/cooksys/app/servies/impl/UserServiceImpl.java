package com.cooksys.app.servies.impl;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.entities.Credentials;
import com.cooksys.app.entities.User;
import com.cooksys.app.exceptions.BadRequestException;
import com.cooksys.app.exceptions.NotAuthorizedException;
import com.cooksys.app.exceptions.NotFoundException;
import com.cooksys.app.mapper.CredentialsMapper;
import com.cooksys.app.mapper.UserMapper;
import com.cooksys.app.repositories.UserRepository;
import com.cooksys.app.servies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;

    @Override
    public void followUser(CredentialsDto credentialsDto, String username) {

        if (!userRepository.existsByUsernameAndPassword(credentialsDto.getUsername(), credentialsDto.getPassword())) {

            throw new NotAuthorizedException("Invalid username or password.");

        } User user = userRepository.findByCredentials(credentialsMapper.DtoToEntity(credentialsDto));

        if (user.getFollowing().stream().findFirst().filter(e -> e.getUsername().equals(username)).isPresent()) {

            throw new BadRequestException("User is already followed.");

        } if (userRepository.existsByUsername(username)) {

            throw new NotFoundException("User not found.");

        } user.getFollowing().add(userRepository.findByUsername(username));
        userRepository.saveAndFlush(user);

    }

}
