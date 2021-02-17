package com.keita.permis.model;

import com.keita.permis.enums.PermitCategory;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Permit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected Date date;
    protected Byte qrCode;
    protected PermitCategory permitCategory;
    //List ne marche pas
    protected String restrictedAreas;
    protected boolean isActive;

    @ManyToMany
    protected List<Administrator> administrators;

}
