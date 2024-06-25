package com.cooksys.app.dtos;

import com.cooksys.app.entities.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class UserResponseDto {

    private String username;

    private Timestamp joined;

    private Profile profile;
}
