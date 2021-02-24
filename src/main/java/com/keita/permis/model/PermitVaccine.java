package com.keita.permis.model;

import com.keita.permis.enums.PermitCategory;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PermitVaccine extends Permit implements Serializable {

    public PermitVaccine() { }

    @Builder
    public PermitVaccine(Date date, Byte qrCode, String restrictedAreas,
                         PermitCategory permitCategory, Citizen citizen) {
        super(date, qrCode, restrictedAreas, permitCategory, citizen);
    }
}
