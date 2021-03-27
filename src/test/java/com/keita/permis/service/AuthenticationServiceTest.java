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
        when(citizenRepository.existsByEmailAndPassword(authForm1.getEmail(), authForm1.getPassword())).thenReturn(true);

        AuthForm authForm2 = new AuthForm("andreMarc12@gmail.com", "andreee", null);
        when(citizenRepository.existsByEmailAndPassword(authForm2.getEmail(), authForm2.getPassword())).thenReturn(false);


        AuthForm authForm3 = new AuthForm("rejArch@gmail.com", "arc123", "arc123");
        when(citizenRepository.existsByEmail(authForm3.getEmail())).thenReturn(true);

        AuthForm authForm4 = new AuthForm("archRej@gmail.com", "arc123", "arc123");
        when(citizenRepository.existsByEmail(authForm4.getEmail())).thenReturn(false);

        AuthForm authForm5 = new AuthForm("rejArch@gmail.com", "arc123", "aarcc123");
        when(citizenRepository.existsByEmail(authForm5.getEmail())).thenReturn(true);

        //Act
        boolean isLoggedInWithAuth1 = authenticationService.authentication(authForm1);
        boolean isNotLoggedInWithAuth2 = authenticationService.authentication(authForm2);
        boolean passwordResetForAuthForm3 = authenticationService.authentication(authForm3);
        boolean passwordResetForAuthForm4 = authenticationService.authentication(authForm4);
        boolean passwordResetForAuthForm5 = authenticationService.authentication(authForm5);

        //Assert
        assertTrue(isLoggedInWithAuth1);
        assertFalse(isNotLoggedInWithAuth2);

        assertTrue(passwordResetForAuthForm3);
        assertFalse(passwordResetForAuthForm4);
        assertFalse(passwordResetForAuthForm5);
    }
}
