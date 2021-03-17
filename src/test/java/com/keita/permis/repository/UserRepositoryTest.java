package com.keita.permis.repository;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

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

    @Test
    void existByEmailAndPassword(){
        //Arrange
        AuthForm authForm1 = new AuthForm("rejArch@gmail.com", "rej123", "");
        AuthForm authForm2 = new AuthForm("andreMarcc15@gmail.com", "marc123", "");
        AuthForm authForm3 = new AuthForm("andreMarc12@gmail.com", "marcc1234", "");

        //Act
        boolean authForm1Log = userRepository.existsByEmailAndPassword(authForm1.getEmail(),authForm1.getPassword());
        boolean authForm2Log = userRepository.existsByEmailAndPassword(authForm2.getEmail(),authForm2.getPassword());
        boolean authForm3Log = userRepository.existsByEmailAndPassword(authForm3.getEmail(),authForm3.getPassword());

        //Assert
        assertTrue(authForm1Log);
        assertFalse(authForm2Log);
        assertFalse(authForm3Log);

    }
}
