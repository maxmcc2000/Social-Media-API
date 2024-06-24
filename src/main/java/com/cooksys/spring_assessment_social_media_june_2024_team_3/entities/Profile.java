package com.cooksys.spring_assessment_social_media_june_2024_team_3.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Embeddable
@Data
public class Profile {

    private String firstName;

    private String lastName;

    @NotBlank
    @Email
    private String email;

    private String phoneNumber;

}
