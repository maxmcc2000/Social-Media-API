package com.cooksys.app.dtos;

import com.cooksys.app.entities.Tweet;
import com.cooksys.app.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class TweetRequestDto {

    private User author;
    private String content;
    private Tweet inReplyTo;
    private Tweet repostOf;

}
