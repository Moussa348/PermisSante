package com.keita.permis.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserSubmitForm implements Serializable {

    @NotNull @NotEmpty
    private String firstName,lastName,gender,email,
            password,passwordAgain,cellNumber,city,socialInsurance,dateOfBirth;

    private String firstNameParent,lastNameParent,emailParent;
    private boolean renewal;

    @Builder
    public UserSubmitForm(@NotNull @NotEmpty String firstName, @NotNull @NotEmpty String lastName, @NotNull @NotEmpty String gender,
                          @NotNull @NotEmpty String email, @NotNull @NotEmpty String password, @NotNull @NotEmpty String passwordAgain,
                          @NotNull @NotEmpty String cellNumber, @NotNull @NotEmpty String city, @NotNull @NotEmpty String socialInsurance,
                          @NotNull @NotEmpty String dateOfBirth, String firstNameParent, String lastNameParent, String emailParent) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.passwordAgain = passwordAgain;
        this.cellNumber = cellNumber;
        this.city = city;
        this.socialInsurance = socialInsurance;
        this.dateOfBirth = dateOfBirth;
        this.firstNameParent = firstNameParent;
        this.lastNameParent = lastNameParent;
        this.emailParent = emailParent;
        this.renewal = false;
    }
}
