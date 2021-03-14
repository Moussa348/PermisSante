package com.keita.permis.dto;

import javax.validation.constraints.*;

import com.keita.permis.utils.ErrorMessage;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthForm implements Serializable {

    @NotNull(message = ErrorMessage.LOGIN_INVALID)
    private String email;

    @NotNull(message = ErrorMessage.PASSWORD_INVALID)
    private String password;

    @NotNull
    private String newPassword;
    private boolean forgotPassword;

    public AuthForm(@NotNull String email, @NotNull String password) {
        this.email = email;
        this.password = password;
        this.forgotPassword = false;
    }

    public AuthForm(@NotNull String email,
                    @NotNull String password,
                    @NotNull String newPassword) {
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
        this.forgotPassword = true;
    }
}
