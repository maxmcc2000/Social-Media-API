package com.cooksys.app.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Embeddable
@Data
public class Profile {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
