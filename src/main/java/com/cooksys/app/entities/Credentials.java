package com.cooksys.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Credentials {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    private String password;
}
