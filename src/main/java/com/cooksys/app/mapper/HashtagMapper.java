package com.cooksys.app.mapper;

import com.cooksys.app.entities.Hashtag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
    HashtagDto entityToDto(Hahstag);

    Hashtag DtoToEntity(HashtagDto);

}
