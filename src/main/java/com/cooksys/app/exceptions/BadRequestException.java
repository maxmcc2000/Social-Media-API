package com.cooksys.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5438170613004964886L;
    private String message;

}
