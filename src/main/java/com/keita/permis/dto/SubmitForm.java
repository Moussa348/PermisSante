package com.keita.permis.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SubmitForm implements Serializable {

    @NotNull
    private String firstName,lastName,gender,email,
            password,passwordAgain,cellNumber,city,socialInsurance,dateOfBirth;

    private String firstNameParent,lastNameParent,emailParent,typePermit;

    public SubmitForm(){}

    @Builder
    public SubmitForm(@NotNull String firstName, @NotNull String lastName, @NotNull String gender,
                      @NotNull String email, @NotNull String password, @NotNull String passwordAgain,
                      @NotNull String cellNumber, @NotNull String city, @NotNull String socialInsurance,
                      @NotNull String dateOfBirth, String firstNameParent, String lastNameParent, String emailParent) {
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
        this.typePermit = "";
    }

}
