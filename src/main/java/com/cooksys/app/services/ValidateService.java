package com.cooksys.app.services;


public interface ValidateService {

    boolean isUsernameAvailable(String username);

    boolean doesUsernameExist(String username);

    boolean doesHashtagExist(String label);
}
