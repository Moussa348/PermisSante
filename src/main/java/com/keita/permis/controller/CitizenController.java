package com.keita.permis.controller;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/citizen")
@CrossOrigin(origins = "http://localhost:4600")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @PostMapping("/authentication")
    public String authentication(@Valid @RequestBody AuthForm authForm) { return citizenService.authentication(authForm); }

    @PostMapping("/registration")
    public Integer registration(@RequestBody Citizen citizen) {
        return citizenService.registration(citizen);
    }

    @GetMapping("/findByEmail/{email}")
    public Citizen viewCitizenInfo(@PathVariable String email){ return citizenService.viewCitizenInfo(email); }
}
