package com.cooksys.app.dtos;

import com.cooksys.app.entities.Profile;
import jakarta.persistence.Embedded;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {

    private String username;

    private Profile profile;
}
