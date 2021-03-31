package com.keita.permis.service;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AuthService {

    @Autowired
    private CitizenRepository citizenRepository;

    public boolean authentication(AuthForm authForm){
        if(!authForm.isForgotPassword())
            return login(authForm);
        else
            return forgotPassword(authForm);
    }

    private boolean login(AuthForm authForm){
        return citizenRepository.existsByEmailAndPassword(authForm.getEmail(), authForm.getPassword());
    }

    //TODO:
    // *Send Email with combination of random number to enter in the form(EmailService)
    // *Implement Spring Security(youtube or teacher) to be able to retrieve info from auth
    private boolean forgotPassword(AuthForm authForm){
        if(citizenRepository.existsByEmail(authForm.getEmail()))
            return authForm.getPassword().equals(authForm.getNewPassword());
        return false;
    }
}
