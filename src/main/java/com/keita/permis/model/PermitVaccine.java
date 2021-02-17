package com.keita.permis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PermitVaccine extends Permit implements Serializable {
}
