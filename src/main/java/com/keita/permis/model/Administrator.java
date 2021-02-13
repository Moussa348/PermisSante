package com.keita.permis.model;

import javax.persistence.Entity;

@Entity
public class Administrator extends User {
    private String role;
}
