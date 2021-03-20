package com.keita.permis.repository;

import com.keita.permis.model.Administrator;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
                        .email("rejArch@gmail.com")
                        .password("rej123").build()

                , Administrator.builder()
                        .email("andreMarc12@gmail.com")
                        .password("marc123").build()
        );

        userRepository.saveAll(users);
    }

    @Test
    void existByEmailAndPassword(){
        //Arrange
        User user1 = Citizen.builder()
                .email("rejArch@gmail.com").password("rej123").build();
        User user2 = Citizen.builder()
                .email("andreMarcc15@gmail.com").password("marc123").build();
        User user3 = Administrator.builder()
                .email("andreMarc12@gmail.com").password("marcc1234").build();

        //Act
        boolean exist = userRepository.existsByEmailAndPassword(user1.getEmail(),user1.getPassword());
        boolean notExist1 = userRepository.existsByEmailAndPassword(user2.getEmail(),user2.getPassword());
        boolean notExist2 = userRepository.existsByEmailAndPassword(user3.getEmail(),user3.getPassword());

        //Assert
        assertTrue(exist);
        assertFalse(notExist1);
        assertFalse(notExist2);
    }

    @Test
    void existByEmail(){
        //Arrange
        User user1 = Citizen.builder()
                .email("rejArch@gmail.com").password("rej123").build();
        User user2 = Citizen.builder()
                .email("andreMarcc15@gmail.com").password("marc123").build();
        //Act
        boolean exist = userRepository.existsByEmail(user1.getEmail());
        boolean notExist = userRepository.existsByEmail(user2.getEmail());

        //Assert
        assertTrue(exist);
        assertFalse(notExist);
    }
}
