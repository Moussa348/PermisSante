package com.keita.permis.service;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.repository.CitizenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthenticationServiceTest {

    @Mock
    CitizenRepository citizenRepository;

    @InjectMocks
    AuthenticationService authenticationService;

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
        when(citizenRepository.existsByEmailAndPassword(authForm1.getEmail(), authForm1.getPassword())).thenReturn(true);
        when(citizenRepository.existsByEmailAndPassword(authForm2.getEmail(), authForm2.getPassword())).thenReturn(false);

        when(citizenRepository.existsByEmail(authForm3.getEmail())).thenReturn(true);
        when(citizenRepository.existsByEmail(authForm4.getEmail())).thenReturn(false);
        when(citizenRepository.existsByEmail(authForm5.getEmail())).thenReturn(true);
        //Assert
        assertTrue(authenticationService.authentication(authForm1));
        assertFalse(authenticationService.authentication(authForm2));

        assertTrue(authenticationService.authentication(authForm3));
        assertFalse(authenticationService.authentication(authForm4));
        assertFalse(authenticationService.authentication(authForm5));
    }
}
