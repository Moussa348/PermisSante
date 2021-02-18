package com.keita.permis.model;

import com.keita.permis.enums.PermitCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PermitVaccine extends Permit implements Serializable {

    public PermitVaccine(){}

    public PermitVaccine(Date date, Byte qrCode, PermitCategory permitCategory,
                         String restrictedAreas, Citizen citizen, Administrator administrator) {
        super(date, qrCode, permitCategory, restrictedAreas, citizen, administrator);
    }
}
