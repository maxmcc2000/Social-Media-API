package com.cooksys.app.dtos;

import com.cooksys.app.entities.Tweet;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class TweetRequestDto {

    //Do not add any fields to this DTO, if you need a dto to additional fields, please create them separately.
    private CredentialsDto credentials;
    private String content;

}
