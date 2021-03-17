package com.keita.permis;

import com.keita.permis.service.PermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PermisApplication {
    @Autowired
    private PermitService permitService;

    public static void main(String[] args) {
        SpringApplication.run(PermisApplication.class, args);
    }

}
