package com.keita.permis.model;

import com.keita.permis.enums.PermitCategory;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Permit implements Serializable {

    @Id
    @GeneratedValue
    protected Long id;

    @NonNull
    protected Date date;

    @NonNull
    protected Byte qrCode;

    @NonNull
    protected String restrictedAreas;
    protected boolean isActive;

    @NonNull
    protected PermitCategory permitCategory;

    @NonNull
    @OneToOne
    private Citizen citizen;

    @ManyToOne
    protected Administrator administrator;

    public Permit() { }

    public Permit(@NonNull Date date, @NonNull Byte qrCode, @NonNull String restrictedAreas,
                  @NonNull PermitCategory permitCategory, @NonNull Citizen citizen) {
        this.date = date;
        this.qrCode = qrCode;
        this.restrictedAreas = restrictedAreas;
        this.permitCategory = permitCategory;
        this.citizen = citizen;
        this.isActive = true;
    }

    /*
            TODO
                -After, In my service related to Permit, I will have a method that will set the permit to inactive,
                 for example for the test after 14 days and then send an email

         */
}
