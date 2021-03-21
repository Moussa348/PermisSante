package com.keita.permis.controller;

import com.keita.permis.dto.UserSubmitForm;
import com.keita.permis.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("citizen")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @PostMapping("/registration")
    public boolean registration(@Valid UserSubmitForm userSubmitForm){
        return citizenService.registration(userSubmitForm);
    }
}
