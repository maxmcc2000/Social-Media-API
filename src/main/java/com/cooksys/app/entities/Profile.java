package com.cooksys.app.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Profile {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
