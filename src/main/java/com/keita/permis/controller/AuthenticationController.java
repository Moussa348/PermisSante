package com.keita.permis.controller;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/authentication")
    public boolean authentication(@Valid @RequestBody AuthForm authForm){
        if(authenticationService.authentication(authForm)) {
            logger.info("A user has logged into the application!");
            return true;
        }else {
            logger.warn("Someone tried to access the application with an unknown account!");
            return false;
        }
    }
}
