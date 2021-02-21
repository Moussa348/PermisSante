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

    public PermitVaccine(){}

    @Builder
    public PermitVaccine(@NonNull Date date, @NonNull Byte qrCode, @NonNull String restrictedAreas,
                         @NonNull PermitCategory permitCategory, @NonNull Citizen citizen) {
        super(date, qrCode, restrictedAreas, permitCategory, citizen);
    }
}
