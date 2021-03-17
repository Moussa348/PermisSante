package com.keita.permis.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserSubmitForm implements Serializable {

    @NotNull
    private String firstName,lastName,gender,email,
            password,cellNumber,city,role,socialInsurance;
    @NotNull
    private Date dateOfBirth;
    private boolean renewal;

    @Builder
    public UserSubmitForm(@NotNull String firstName, @NotNull String lastName, @NotNull String gender,
                          @NotNull String email,@Size(min = 8) @NotNull String password,
                          @NotNull String cellNumber, @NotNull String city, @NotNull String role,
                          @NotNull String socialInsurance, @NotNull Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.cellNumber = cellNumber;
        this.city = city;
        this.role = role;
        this.socialInsurance = socialInsurance;
        this.dateOfBirth = dateOfBirth;
        this.renewal = false;
    }
}
