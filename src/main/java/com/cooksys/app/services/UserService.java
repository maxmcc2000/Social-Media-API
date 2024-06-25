package com.cooksys.app.services;

import com.cooksys.app.dtos.CredentialsDto;

public interface UserService {

    void followUser(CredentialsDto credentials, String username);

}
