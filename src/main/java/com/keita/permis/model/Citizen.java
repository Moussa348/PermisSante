package com.keita.permis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Citizen extends User implements Serializable {

    @NonNull
    private String socialInsurance;
    private boolean isVaccinated;
    private boolean isTested;

    @OneToOne
    private Permit permit;

    public Citizen() { }

    public Citizen(String firstName, String lastName, String gender,
                   String email, String password, String cellNumber,
                   String city, Date dateOfBirth, String socialInsurance,
                   boolean isVaccinated, boolean isTested, Permit permit) {

        super(firstName, lastName, gender, email, password, cellNumber, city, dateOfBirth);
        this.socialInsurance = socialInsurance;
        this.isVaccinated = isVaccinated;
        this.isTested = isTested;
        this.permit = permit;
    }
}
