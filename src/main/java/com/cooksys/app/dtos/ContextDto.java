package com.cooksys.app.dtos;

import com.cooksys.app.entities.Tweet;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContextDto {

    private Tweet target;
    //changed to List
    //use arrayLists for maximum effectiveness
    private List<Tweet> before;
    private List<Tweet> after;

}
