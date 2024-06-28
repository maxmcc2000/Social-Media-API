package com.cooksys.app.dtos;

import com.cooksys.app.entities.Tweet;
import com.cooksys.app.dtos.TweetResponseDto;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContextDto {

    private TweetResponseDto target;
    //changed to List
    //use arrayLists for maximum effectiveness
    private List<TweetResponseDto> before;
    private List<TweetResponseDto> after;

}
