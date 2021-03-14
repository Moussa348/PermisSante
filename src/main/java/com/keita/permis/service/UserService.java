package com.keita.permis.service;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean authentication(AuthForm authForm){
        if(!authForm.isForgotPassword())
            return login(authForm);
        else
            return forgotPassword(authForm);
    }

    private boolean login(AuthForm authForm){
        return userRepository.existsByEmailAndPassword(authForm.getEmail(), authForm.getPassword());
    }

    private boolean forgotPassword(AuthForm authForm){
        if(userRepository.existByEmail(authForm.getEmail()))
            return authForm.getPassword().equals(authForm.getNewPassword());
        return false;
    }
}
