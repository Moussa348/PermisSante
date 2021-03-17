package com.keita.permis.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
public class Citizen extends User implements Serializable {

    private String socialInsurance;
    private boolean isVaccinated;

    public Citizen() { }

    @Builder
    public Citizen(String firstName, String lastName, String gender,
                   String email, String password, String cellNumber,
                   String city, LocalDate dateOfBirth, String socialInsurance) {
        super(firstName, lastName, gender, email, password, cellNumber, city, dateOfBirth);
        this.socialInsurance = socialInsurance;
        this.isVaccinated = false;
    }
}
