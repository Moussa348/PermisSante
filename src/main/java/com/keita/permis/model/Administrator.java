package com.keita.permis.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Administrator extends User implements Serializable {

    private String role;

    public Administrator(){}

    @Builder
    public Administrator(@NonNull String firstName, @NonNull String lastName, @NonNull String gender, @NonNull String email,
                         @NonNull String password, @NonNull String cellNumber, @NonNull String city, @NonNull Date dateOfBirth, String role) {
        super(firstName, lastName, gender, email, password, cellNumber, city, dateOfBirth);
        this.role = role;
    }
}
