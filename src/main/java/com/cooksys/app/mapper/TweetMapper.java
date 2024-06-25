package com.cooksys.app.mapper;

import org.mapstruct.Mapper;

import com.cooksys.app.entities.Tweet;
import com.cooksys.app.dtos.TweetResponseDto;

@Mapper(componentModel = "spring")
public interface TweetMapper {

    TweetResponseDto entityTodto(Tweet entity);

    Tweet TweetToEntity(Tweet pDto);
}
