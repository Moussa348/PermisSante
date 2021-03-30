package com.keita.permis.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestPermitForm {
    @NotNull
    private String email,password;

    private String cellNumber,city,typePermit;

    public RequestPermitForm(){}

    @Builder
    public RequestPermitForm(@NotNull String email, @NotNull String password, String cellNumber, String city) {
        this.email = email;
        this.password = password;
        this.cellNumber = cellNumber;
        this.city = city;
        this.typePermit = "";
    }
}
