package com.cooksys.app.mapper;

import com.cooksys.app.dtos.TweetRequestDto;
import org.mapstruct.Mapper;

import com.cooksys.app.entities.Tweet;
import com.cooksys.app.dtos.TweetResponseDto;

@Mapper(componentModel = "spring")
public interface TweetMapper {

    TweetResponseDto entityTodto(Tweet entity);

    Tweet requestDtoToEntity(TweetRequestDto tweetRequestDto);

    //Tweet TweetToEntity(Tweet pDto);
    //removed "TweetToEntity". Tweet is an Entity so unneeded and uncompilable
}
