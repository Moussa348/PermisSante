package com.keita.permis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keita.permis.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@ToString(callSuper = true)
public class Citizen implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName, lastName, gender, email,
            password, cellNumber, city, socialInsurance;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private LocalDate registrationDate;
    private boolean vaccinated;
    private boolean active;
    private Role role;

    @OneToOne
    private Citizen parent;

    public Citizen() {
    }

    @Builder
    public Citizen(String firstName, String lastName, String gender,
                   String email, String password, String cellNumber,
                   String city, LocalDate dateOfBirth, String socialInsurance,Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.cellNumber = cellNumber;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.registrationDate = LocalDate.now();
        this.socialInsurance = socialInsurance;
        this.vaccinated = false;
        this.active = true;
        this.role = role;
    }
}
