package com.keita.permis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Citizen extends User implements Serializable {

    private String socialInsurance;
    private boolean isVaccinated;
    private boolean isTested;
}
