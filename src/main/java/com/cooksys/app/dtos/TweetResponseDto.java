package com.cooksys.app.dtos;

import com.cooksys.app.entities.Tweet;
import com.cooksys.app.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data
public class TweetResponseDto {
	private long id; 
    private UserResponseDto author;
    private Timestamp posted;
    private String content;
    private TweetResponseDto inReplyTo;
    private TweetResponseDto repostOf;
    //private List<HashtagResponseDto> hashtags;

}
