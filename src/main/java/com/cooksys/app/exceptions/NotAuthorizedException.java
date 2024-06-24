package com.cooksys.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3583563528342429842L;
    private String message;

}