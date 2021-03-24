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
    private LocalDate creationDate;
    private LocalDate expirationDate;
    private byte[] qrCode;
    private String restrictedAreas;
    private PermitCategory permitCategory;
    private PermitType permitType;
    private int lifeTime;
    private boolean isActive;

    @OneToOne
    private Citizen citizen;

    public Permit() { }

    @Builder
    public Permit(String restrictedAreas,
                  PermitCategory permitCategory, Citizen citizen, PermitType permitType) {
        this.creationDate = LocalDate.now();
        this.restrictedAreas = restrictedAreas;
        this.permitCategory = permitCategory;
        this.citizen = citizen;

        if(permitType.equals(PermitType.VACCINE)){
            this.permitType = PermitType.VACCINE;
            this.lifeTime = 6;
            this.expirationDate = LocalDate.now().plusMonths(lifeTime);
        }
        if(permitType.equals(PermitType.TEST)){
            this.permitType = PermitType.TEST;
            this.lifeTime = 15;
            this.expirationDate = LocalDate.now().plusDays(lifeTime);
        }
        isActive = true;
    }
}
