package com.keita.permis.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Serializable {

    @Id
    @GeneratedValue
    protected Long id;

    @NonNull
    protected String firstName,lastName,gender,email,password,cellNumber,city;

    @NonNull
    protected Date dateOfBirth;

    protected boolean isActive;

    public User(){}

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String gender, @NonNull String email,
                @NonNull String password, @NonNull String cellNumber, @NonNull String city, @NonNull Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.cellNumber = cellNumber;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.isActive = true;
    }
}
