package com.keita.permis.controller;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.dto.SubmitForm;
import com.keita.permis.enums.PermitType;
import com.keita.permis.service.CitizenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/citizen")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;
    Logger logger = LoggerFactory.getLogger(CitizenController.class);

    @PostMapping("/authentication")
    public String authentication(@Valid @RequestBody AuthForm authForm){
        return citizenService.authentication(authForm);
    }

    @PostMapping("/registration")
    public boolean registration(@Valid @RequestBody SubmitForm submitForm) {
        String response = citizenService.getPermitTypeIfInhabitantIsValidWithSocialInsurance(submitForm.getSocialInsurance());

        if(!response.isEmpty()){
            submitForm.setTypePermit(response);
            logger.info(response);
            return citizenService.registration(submitForm);
        }
        return false;
    }
}
