package com.cooksys.app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {

    @JsonProperty("credentials")
    private CredentialsDto credentialsDto;

    @JsonProperty("profile")
    private ProfileDto profileDto;

}
