package com.keita.permis.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestPermitForm {
    @NotNull
    private String email,password;

    private String number,city;

    public RequestPermitForm(){}

    @Builder
    public RequestPermitForm(@NotNull String email, @NotNull String password, String number, String city) {
        this.email = email;
        this.password = password;
        this.number = number;
        this.city = city;
    }
}
