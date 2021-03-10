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
    public void insertData()  {
        List<User> users = Arrays.asList(
                Citizen.builder()
                        .firstName("Massou")
                        .lastName("massou")
                        .gender("M")
                        .email("massou@gmail.com")
                        .password("massou123")
                        .cellNumber("5143435478")
                        .city("mtl")
                        .dateOfBirth(LocalDate.of(1996,11,2))
                        .socialInsurance("MASSOUMA980725").build()

                , Citizen.builder()
                        .firstName("Cancre")
                        .lastName("Cancre")
                        .gender("M")
                        .email("cancre@gmail.com")
                        .password("cancre123")
                        .cellNumber("5143435478")
                        .city("mtl")
                        .dateOfBirth(LocalDate.of(1996,11,2))
                        .socialInsurance("CANCC961022").build(),
                new Administrator()
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
        AuthForm authForm = AuthForm.builder()
                .email("massou@gmail.com").password("massou123").build();

        AuthForm authForm2 = AuthForm.builder()
                .email("cancre@gmail.com").password("massou123").build();
        //Act
        Mockito.when(userRepository.existsByEmailAndPassword(authForm.getEmail(), authForm.getPassword())).thenReturn(true);
        Mockito.when(userRepository.existsByEmailAndPassword(authForm2.getEmail(), authForm2.getPassword())).thenReturn(false);
        //Assert
        assertTrue(userService.authentication(authForm) && !userService.authentication(authForm2) );
    }
}
