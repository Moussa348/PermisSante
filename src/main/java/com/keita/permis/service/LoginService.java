package com.keita.permis.service;

import com.keita.permis.dto.LoginForm;
import com.keita.permis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public boolean login(LoginForm loginForm){
        return userRepository.existsByEmailAndPassword(loginForm.getEmail(),loginForm.getPassword());
    }
}
