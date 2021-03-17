package com.keita.permis.dto;

import javax.validation.constraints.*;

import com.keita.permis.utils.ErrorMessage;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Data
public class AuthForm implements Serializable {

    @NotNull(message = ErrorMessage.LOGIN_INVALID)
    private String email;

    @NotNull(message = ErrorMessage.PASSWORD_INVALID)
    private String password;
    private String newPassword;
    private String secretKey;
    private boolean forgotPassword = false;

    public AuthForm() { }

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
