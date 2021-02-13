package com.keita.permis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Administrator extends User implements Serializable {
    private String role;

    @ManyToMany(mappedBy = "administrators")
    private List<Permit> permits;
}
