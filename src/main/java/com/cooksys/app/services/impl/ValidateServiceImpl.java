package com.cooksys.app.services.impl;

import com.cooksys.app.repositories.HashtagRepository;
import com.cooksys.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import com.cooksys.app.services.ValidateService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService{

    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    @Override
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByCredentialsUsername(username);
    }

    @Override
    public boolean doesUsernameExist(String username) {
        return userRepository.existsByCredentialsUsername(username);
    }

    @Override
    public boolean doesHashtagExist(String label) {
        return hashtagRepository.existsByLabel(label);
    }

}
