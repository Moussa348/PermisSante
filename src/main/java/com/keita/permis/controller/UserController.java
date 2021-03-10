package com.keita.permis.controller;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<Boolean> login(@Valid @RequestBody AuthForm authForm){
        if(userService.authentication(authForm)) {
            logger.info("A user has logged into the application!");
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            logger.warn("Someone tried to access the application with an unknown account!");
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }
}
