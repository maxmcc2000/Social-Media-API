package com.cooksys.app.mapper;

import com.cooksys.app.dtos.CredentialsDto;
import com.cooksys.app.entities.Credentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface CredentialsMapper {

    CredentialsDto entityToDto(Credentials entity);

    Credentials DtoToEntity(CredentialsDto credDTO);

}
