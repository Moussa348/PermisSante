package com.keita.permis.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginForm {
    @NotNull
    private String email,password;

    public LoginForm(){}

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
