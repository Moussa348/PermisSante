package com.keita.permis.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Citizen extends User implements Serializable {

    @NonNull
    private String socialInsurance;
    private boolean isVaccinated;

    public Citizen() { }

    @Builder
    public Citizen(@NonNull String firstName, @NonNull String lastName, @NonNull String gender,
                   @NonNull String email, @NonNull String password, @NonNull String cellNumber,
                   @NonNull String city, @NonNull Date dateOfBirth, @NonNull String socialInsurance) {
        super(firstName, lastName, gender, email, password, cellNumber, city, dateOfBirth);
        this.socialInsurance = socialInsurance;
        this.isVaccinated = false;
    }
}
