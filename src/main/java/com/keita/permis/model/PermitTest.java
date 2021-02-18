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
public class PermitTest extends Permit implements Serializable {
    private int LIFE_TIME;

    public PermitTest(){}

    public PermitTest(Date date, Byte qrCode, PermitCategory permitCategory, String restrictedAreas,
                      Citizen citizen, Administrator administrator, int LIFE_TIME) {
        super(date, qrCode, permitCategory, restrictedAreas, citizen, administrator);
        this.LIFE_TIME = LIFE_TIME;
    }
}
