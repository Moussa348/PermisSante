package com.keita.permis.dto;

import javax.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthForm implements Serializable {

    @NotNull
    private String email,password;

    public AuthForm() { }

    @Builder
    public AuthForm(@NotNull String email,
                    @NotNull String password) {
        this.email = email;
        this.password = password;
    }

}
