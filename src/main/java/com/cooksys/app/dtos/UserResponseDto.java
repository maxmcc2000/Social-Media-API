package com.cooksys.app.dtos;

import jakarta.persistence.Embedded;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {

    private String username;

    @Embedded
    private Profile profile;
}
