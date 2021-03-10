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
        return false;
    }

    private boolean login(AuthForm authForm){
        return userRepository.existsByEmailAndPassword(authForm.getEmail(), authForm.getPassword());
    }
}
