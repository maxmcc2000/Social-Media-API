package com.cooksys.app.dtos;

import com.cooksys.app.entities.Tweet;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContextDto {

    private Tweet target;
    //The instructions seemed to imply they 'before' and 'after' should be an array, so that is what I made them. Feel free to change to
    //another date structure as needed.
    private Tweet[] before;
    private Tweet[] after;

}
