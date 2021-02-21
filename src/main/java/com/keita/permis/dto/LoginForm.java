package com.keita.permis.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
public class LoginForm {
    @NotNull
    private String email,password;

    public LoginForm(){}

    public LoginForm(@NonNull String email,@NonNull String password) {
        this.email = email;
        this.password = password;
    }
}
