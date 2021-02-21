package com.keita.permis.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    protected Long id;

    @NonNull
    protected String firstName,lastName,gender,email,password,cellNumber,city;

    @NonNull
    protected Date dateOfBirth;

    protected boolean isActive;

    public User(){}

}
