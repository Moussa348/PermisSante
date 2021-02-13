package com.keita.permis.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class User {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String firstName,lastName,gender,email,password,cellNumber,city;
    private Date dateOfBirth;

}
