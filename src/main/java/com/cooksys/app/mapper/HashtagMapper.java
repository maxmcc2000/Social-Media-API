package com.cooksys.app.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
    HashtagDto entityToDto(Hahstag);

    Hashtag DtoToEntity(HashtagDto);

}
