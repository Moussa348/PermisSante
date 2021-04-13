package com.keita.permis.controller;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.dto.SubmitForm;
import com.keita.permis.enums.PermitType;
import com.keita.permis.model.Citizen;
import com.keita.permis.service.CitizenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/citizen")
@CrossOrigin(origins = "http://localhost:4600")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;
    Logger logger = LoggerFactory.getLogger(CitizenController.class);

    @PostMapping("/authentication")
    public String authentication(@Valid @RequestBody AuthForm authForm) {
        return citizenService.authentication(authForm);
    }

    @PostMapping("/registration")
    public Integer registration(@RequestBody Citizen citizen) {
        return citizenService.registration(citizen);
    }
    /*

    public boolean registration(@Valid @RequestBody SubmitForm submitForm) {
        return citizenService.registration(submitForm);
    }
     */
}
