package com.keita.permis.controller;

import com.keita.permis.dto.LoginForm;
import com.keita.permis.service.LoginService;
import lombok.NonNull;
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

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @NonNull LoginForm loginForm){
        if(loginService.login(loginForm))
            return new ResponseEntity<>("You are now logged in!", HttpStatus.OK);
        return new ResponseEntity<>("Sorry we could not identify you...",HttpStatus.BAD_REQUEST);
    }
}
