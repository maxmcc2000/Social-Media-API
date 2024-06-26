package com.cooksys.app.mapper;

import com.cooksys.app.dtos.TweetRequestDto;
import org.mapstruct.Mapper;

import com.cooksys.app.entities.Tweet;
import com.cooksys.app.dtos.TweetResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TweetMapper {

    TweetResponseDto entityTodto(Tweet entity);

    Tweet requestDtoToEntity(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> entitiesToDtos(List<Tweet> tweets);

    //Tweet TweetToEntity(Tweet pDto);
    //removed "TweetToEntity". Tweet is an Entity so unneeded and uncompilable
}
