package com.keita.permis.service;

import com.keita.permis.dto.LoginForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.User;
import com.keita.permis.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginServiceTest {

    @Mock
     UserRepository userRepository;

    @Test
    public void login() {
        //Arrange
        LoginForm loginForm = new LoginForm("massou@gmail.com","massou123");
        LoginForm loginForm2 = new LoginForm("cancre@gmail.com","massou123");
        //Act
        Mockito.when(userRepository.existsByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword())).thenReturn(true);
        boolean isLogged = userRepository.existsByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
        Mockito.when(userRepository.existsByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword())).thenReturn(false);
        boolean isNotLogged = userRepository.existsByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
        //Assert
        assertTrue(isLogged);
        assertFalse(isNotLogged);
    }
}
