package com.keita.permis.controller;

import com.keita.permis.dto.LoginForm;
import com.keita.permis.service.LoginService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Boolean> login(@RequestBody @NonNull LoginForm loginForm){
        if(loginService.login(loginForm)) {
            logger.info("A user has logged into the application!");
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            logger.warn("Someone tried to access the application with an unknown account!");
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }
}
