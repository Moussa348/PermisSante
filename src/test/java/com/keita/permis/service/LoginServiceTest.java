package com.keita.permis.service;

import com.keita.permis.dto.LoginForm;
import com.keita.permis.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    /*
        -@Spy
        -Cas normal,acune surprise des resultats
        -cas anormal
        -cas improbable
     */

    @Test
    public void login() {
        //Arrange
        LoginForm loginForm = new LoginForm("massou@gmail.com", "massou123");
        LoginForm loginForm2 = new LoginForm("cancre@gmail.com", "massou123");
        //Act
        Mockito.when(userRepository.existsByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword())).thenReturn(true);
        Mockito.when(userRepository.existsByEmailAndPassword(loginForm2.getEmail(), loginForm2.getPassword())).thenReturn(false);
        //Assert
        assertTrue(loginService.login(loginForm) && !loginService.login(loginForm2) );
    }
}
