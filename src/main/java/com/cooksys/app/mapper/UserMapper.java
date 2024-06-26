package com.cooksys.app.mapper;

import com.cooksys.app.dtos.UserRequestDto;
import com.cooksys.app.dtos.UserResponseDto;
import com.cooksys.app.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TweetMapper.class, CredentialsMapper.class, ProfileMapper.class})
public interface UserMapper {

    UserResponseDto entityToDto(User user);

    List<UserResponseDto> entitiesToDtos(List<User> users);

    User DtoToEntity(UserRequestDto userRequestDto);
}
