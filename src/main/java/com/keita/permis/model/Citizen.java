package com.keita.permis.model;

import javax.persistence.Entity;

@Entity
public class Citizen extends User {
    private String socialInsurance;
    private boolean isVaccinated;
    private boolean isTested;
}
