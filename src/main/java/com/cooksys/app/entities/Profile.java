package com.cooksys.app.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Annotation;

@Embeddable
@Data
public class Profile implements org.springframework.context.annotation.Profile {

    private String firstName;

    private String lastName;

    @NotBlank
    @Email
    private String email;

    private String phone;

    @Override
    public String[] value() {
        return new String[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
