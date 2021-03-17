package com.keita.permis.model;

import com.keita.permis.enums.PermitCategory;
import com.keita.permis.enums.PermitType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Permit implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate dateCreation;
    private LocalDate dateExpiration;
    private byte[] qrCode;
    private String restrictedAreas;
    private boolean isActive;
    private PermitCategory permitCategory;
    private PermitType permitType;
    private int lifeTime;

    @OneToOne
    private Citizen citizen;

    public Permit() { }

    @Builder
    public Permit(String restrictedAreas,
                  PermitCategory permitCategory, Citizen citizen, PermitType permitType) {
        this.dateCreation = LocalDate.now();
        this.restrictedAreas = restrictedAreas;
        this.permitCategory = permitCategory;
        this.permitType = permitType;
        this.citizen = citizen;
        this.isActive = true;

        if(permitType.equals(PermitType.VACCINE)){
            this.lifeTime = 6;
            this.dateExpiration = LocalDate.now().plusMonths(lifeTime);
        }else{
            this.lifeTime = 15;
            this.dateExpiration = LocalDate.now().plusDays(lifeTime);
        }
    }

    /*
            TODO
                -After, In my service related to Permit, I will have a method that will set the permit to inactive,
                 for example for the test after 14 days and then send an email

         */
}
