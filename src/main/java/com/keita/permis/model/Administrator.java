package com.keita.permis.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Administrator extends User implements Serializable {

    private String role;

    public Administrator() {
    }

    @Builder
    public Administrator(String firstName, String lastName, String gender, String email,
                         String password, String cellNumber, String city, Date dateOfBirth, String role) {
        super(firstName, lastName, gender, email, password, cellNumber, city, dateOfBirth);
        this.role = role;
    }
}
