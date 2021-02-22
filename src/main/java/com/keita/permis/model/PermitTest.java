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
public class PermitTest extends Permit implements Serializable {

    @NonNull
    private int LIFE_TIME;

    public PermitTest(){}

    @Builder
    public PermitTest(@NonNull Date date, @NonNull Byte qrCode, @NonNull String restrictedAreas,
                      @NonNull PermitCategory permitCategory, @NonNull Citizen citizen, @NonNull int LIFE_TIME) {
        super(date, qrCode, restrictedAreas, permitCategory, citizen);
        this.LIFE_TIME = LIFE_TIME;
    }
}
