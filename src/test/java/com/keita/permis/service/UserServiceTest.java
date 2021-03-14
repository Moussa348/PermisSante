package com.keita.permis.service;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.model.Administrator;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.User;
import com.keita.permis.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeAll
    public void insertData() {
        List<User> users = Arrays.asList(
                Citizen.builder()
                        .firstName("Rejean")
                        .lastName("Archambault")
                        .gender("M")
                        .email("rejArch@gmail.com")
                        .password("rej123")
                        .cellNumber("5143435478")
                        .city("Trois-Rivieres")
                        .dateOfBirth(LocalDate.of(1996, 11, 2))
                        .socialInsurance("ARCA96110214").build()

                , Citizen.builder()
                        .firstName("Marc")
                        .lastName("Andre")
                        .gender("M")
                        .email("andreMarc12@gmail.com")
                        .password("marc123")
                        .cellNumber("4389765490")
                        .city("Quebec")
                        .dateOfBirth(LocalDate.of(1968, 9, 13))
                        .socialInsurance("ANDM68091315").build()
        );

        userRepository.saveAll(users);
    }

    /*
        -@Spy
        -Cas normal,acune surprise des resultats
        -cas anormal
        -cas improbable
     */

    @Test
    public void authentication() {
        //Arrange

        AuthForm authForm1 = new AuthForm("rejArch@gmail.com", "rej123");
        AuthForm authForm2 = new AuthForm("andreMarc12@gmail.com", "andreee");

        AuthForm authForm3 = new AuthForm("rejArch@gmail.com", "arc123", "arc123");
        AuthForm authForm4 = new AuthForm("archRej@gmail.com", "arc123", "arc123");
        AuthForm authForm5 = new AuthForm("rejArch@gmail.com", "arc123", "aarcc123");

        //Act
        Mockito.when(userRepository.existsByEmailAndPassword(authForm1.getEmail(), authForm1.getPassword())).thenReturn(true);
        Mockito.when(userRepository.existsByEmailAndPassword(authForm2.getEmail(), authForm2.getPassword())).thenReturn(false);

        Mockito.when(userRepository.existsByEmail(authForm3.getEmail())).thenReturn(true);
        Mockito.when(userRepository.existsByEmail(authForm4.getEmail())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(authForm5.getEmail())).thenReturn(true);
        //Assert
        assertTrue(userService.authentication(authForm1));
        assertFalse(userService.authentication(authForm2));

        assertTrue(userService.authentication(authForm3));
        assertFalse(userService.authentication(authForm4));
        assertFalse(userService.authentication(authForm5));
    }
}
