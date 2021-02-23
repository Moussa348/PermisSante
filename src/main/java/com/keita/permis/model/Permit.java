package com.keita.permis.model;

import com.keita.permis.enums.PermitCategory;
import lombok.Data;

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
    protected Date date;
    protected Byte qrCode;
    protected String restrictedAreas;
    protected boolean isActive;
    protected PermitCategory permitCategory;

    @OneToOne
    private Citizen citizen;

    @ManyToOne
    protected Administrator administrator;

    public Permit() { }

    public Permit(Date date, Byte qrCode, String restrictedAreas,
                  PermitCategory permitCategory, Citizen citizen) {
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
