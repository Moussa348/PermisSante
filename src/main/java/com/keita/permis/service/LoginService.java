package com.keita.permis.service;

import com.keita.permis.dto.LoginForm;
import com.keita.permis.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public boolean login(@NonNull LoginForm loginForm){
        return userRepository.existsByEmailAndPassword(loginForm.getEmail(),loginForm.getPassword());
    }
}
