package com.cooksys.app.mapper;

import com.cooksys.app.dtos.HashtagRequestDto;
import com.cooksys.app.dtos.HashtagResponseDto;
import com.cooksys.app.entities.Hashtag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
	HashtagResponseDto entityToDto(Hashtag entity);

    Hashtag dtoToEntity(HashtagRequestDto hashtageRequestDto);

}
