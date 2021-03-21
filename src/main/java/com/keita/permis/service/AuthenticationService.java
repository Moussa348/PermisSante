package com.keita.permis.service;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;


@Service
public class AuthenticationService {

    @Autowired
    private CitizenRepository citizenRepository;

    public boolean authentication(@NotNull AuthForm authForm){
        if(!authForm.isForgotPassword())
            return login(authForm);
        else
            return forgotPassword(authForm);
    }

    private boolean login(AuthForm authForm){
        return citizenRepository.existsByEmailAndPassword(authForm.getEmail(), authForm.getPassword());
    }

    //TODO: Send Email with combination of random number to enter in the form(EmailService)
    private boolean forgotPassword(AuthForm authForm){
        if(citizenRepository.existsByEmail(authForm.getEmail()))
            return authForm.getPassword().equals(authForm.getNewPassword());
        return false;
    }
}
