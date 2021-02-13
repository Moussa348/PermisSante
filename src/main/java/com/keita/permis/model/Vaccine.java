package com.keita.permis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Vaccine extends Permit implements Serializable {
}
