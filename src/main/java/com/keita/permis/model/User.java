package com.keita.permis.model;

import lombok.Data;

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
    protected String firstName,lastName,gender,email,password,cellNumber,city;
    protected Date dateOfBirth;
    protected boolean isActive;

    public User(){}

    public User(String firstName, String lastName, String gender, String email,
                String password, String cellNumber, String city, Date dateOfBirth) {
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
