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
    @GeneratedValue (strategy = GenerationType.AUTO)
    protected Long id;
    protected String firstName,lastName,gender,email,password,cellNumber,city;
    protected Date dateOfBirth;

}
