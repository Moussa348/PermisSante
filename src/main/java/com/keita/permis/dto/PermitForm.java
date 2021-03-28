package com.keita.permis.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PermitForm {
    private String email,password,number,city;

    @Builder
    public PermitForm(@NotNull String email,@NotNull String password, String number, String city) {
        this.email = email;
        this.password = password;
        this.number = number;
        this.city = city;
    }
}
