package com.cooksys.app.mapper;

import com.cooksys.app.dtos.ProfileDto;
import com.cooksys.app.entities.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto entityToDto(Profile entity);

    Profile DtoToEntity(ProfileDto pDto);
}
