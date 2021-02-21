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
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Permit implements Serializable {

    @Id
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

    /*
            TODO
                -After, In my service related to Permit, I will have a method that will set the permit to inactive,
                 for example for the test after 14 days and then send an email

         */
}
