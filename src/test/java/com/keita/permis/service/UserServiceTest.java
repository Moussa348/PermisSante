package com.keita.permis.service;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    /*
        -@Spy
        -Cas normal,acune surprise des resultats
        -cas anormal
        -cas improbable
     */

    @Test
    public void authentication() {
        //Arrange
        AuthForm authForm1 = new AuthForm("rejArch@gmail.com", "rej123", "");
        AuthForm authForm2 = new AuthForm("andreMarc12@gmail.com", "andreee", null);

        AuthForm authForm3 = new AuthForm("rejArch@gmail.com", "arc123", "arc123");
        AuthForm authForm4 = new AuthForm("archRej@gmail.com", "arc123", "arc123");
        AuthForm authForm5 = new AuthForm("rejArch@gmail.com", "arc123", "aarcc123");

        //Act
        when(userRepository.existsByEmailAndPassword(authForm1.getEmail(), authForm1.getPassword())).thenReturn(true);
        when(userRepository.existsByEmailAndPassword(authForm2.getEmail(), authForm2.getPassword())).thenReturn(false);

        when(userRepository.existsByEmail(authForm3.getEmail())).thenReturn(true);
        when(userRepository.existsByEmail(authForm4.getEmail())).thenReturn(false);
        when(userRepository.existsByEmail(authForm5.getEmail())).thenReturn(true);
        //Assert
        assertTrue(userService.authentication(authForm1));
        assertFalse(userService.authentication(authForm2));

        assertTrue(userService.authentication(authForm3));
        assertFalse(userService.authentication(authForm4));
        assertFalse(userService.authentication(authForm5));
    }
}
