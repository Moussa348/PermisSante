package com.keita.permis.dto;

import javax.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthForm implements Serializable {

    //TODO: will have to generate a secret key when forgotPassword is null, to send an email

    @NotNull
    private String email,password;

    private String newPassword;
    private boolean forgotPassword = false;

    public AuthForm() { }

    @Builder
    public AuthForm(@NotNull String email,
                    @NotNull String password,
                    String newPassword) {
        this.email = email;
        this.password = password;

        if(newPassword != null) {
            this.newPassword = newPassword;
            this.forgotPassword = !newPassword.isEmpty();
        }
    }

}
