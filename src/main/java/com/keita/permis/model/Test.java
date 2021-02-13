package com.keita.permis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Test extends Permit implements Serializable {
    private int LIFE_TIME;
}
