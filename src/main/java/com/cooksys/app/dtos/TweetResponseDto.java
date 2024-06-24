package com.cooksys.app.dtos;

import com.cooksys.app.entities.Tweet;
import com.cooksys.app.entities.User;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class TweetResponseDto {

    private User author;
    private Timestamp posted;
    private String content;
    private Tweet inReplyTo;
    private Tweet repostOf;

}
