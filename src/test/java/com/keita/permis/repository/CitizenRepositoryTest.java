package com.keita.permis.repository;

import com.keita.permis.model.Citizen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = "spring.datasource.initialization-mode=never")//Désactivé,car il insert les donnees de mon data.sql
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CitizenRepositoryTest {

    @Autowired
    CitizenRepository citizenRepository;

    @BeforeAll
    public void insertData() {
        List<Citizen> citizens = Arrays.asList(
                Citizen.builder()
                        .firstName("Rejean").lastName("Archambault")
                        .email("rejArch@gmail.com").password("rej123")
                        .cellNumber("5143456789").city("Gaspesie")
                        .dateOfBirth(LocalDate.of(1996,12,23))
                        .socialInsurance("REJEAR1239892").city("Gaspesie").build(),
                Citizen.builder()
                        .firstName("Antoine").lastName("Fafard")
                        .email("fafaAn@gmail.com").password("ant123")
                        .cellNumber("5143456789").city("Laval")
                        .dateOfBirth(LocalDate.of(1996,12,23))
                        .socialInsurance("FAFAAN1232424").build()
        );
        citizenRepository.saveAll(citizens);
    }

    @Test
    void existByEmail(){
        //Arrange
        Citizen citizen1 = Citizen.builder()
                .email("rejArch@gmail.com").password("rej123").build();
        Citizen citizen2 = Citizen.builder()
                .email("andreMarcc15@gmail.com").password("marc123").build();
        //Act
        boolean exist = citizenRepository.existsByEmail(citizen1.getEmail());
        boolean notExist = citizenRepository.existsByEmail(citizen2.getEmail());

        //Assert
        assertTrue(exist);
        assertFalse(notExist);
    }

    @Test
    void findByEmail(){
        Citizen citizen1 = Citizen.builder()
                .email("rejArch@gmail.com").build();
        Citizen citizen2 = Citizen.builder()
                .email("fafaAnnn@gmail.com").build();
        //Act
        Optional<Citizen> optionalCitizen1 = citizenRepository
                .findByEmail(citizen1.getEmail());
        Optional<Citizen> optionalCitizen2 = citizenRepository
                .findByEmail(citizen2.getEmail());

        //Assert
        assertTrue(optionalCitizen1.isPresent());
        assertFalse(optionalCitizen2.isPresent());
    }

    @Test
    void findBySocialInsurance(){
        Citizen citizen1 = Citizen.builder()
                .socialInsurance("REJEAR1239892").build();
        Citizen citizen2 = Citizen.builder()
                .email("11111111").build();
        //Act
        Optional<Citizen> optionalCitizen1 = citizenRepository
                .findBySocialInsurance(citizen1.getSocialInsurance());
        Optional<Citizen> optionalCitizen2 = citizenRepository
                .findBySocialInsurance(citizen2.getSocialInsurance());

        //Assert
        assertTrue(optionalCitizen1.isPresent());
        assertFalse(optionalCitizen2.isPresent());
    }

    @Test
    void findByEmailAndPassword(){
        Citizen citizen1 = Citizen.builder()
                .email("rejArch@gmail.com").password("rej123").build();
        Citizen citizen2 = Citizen.builder()
                .email("fafaAn@gmail.com").password("allo123").build();
        //Act
        Optional<Citizen> optionalCitizen1 = citizenRepository
                .findByEmailAndPassword(
                        citizen1.getEmail(),citizen1.getPassword());
        Optional<Citizen> optionalCitizen2 = citizenRepository
                .findByEmailAndPassword(
                        citizen2.getEmail(),citizen2.getPassword());

        //Assert
        assertTrue(optionalCitizen1.isPresent());
        assertFalse(optionalCitizen2.isPresent());
    }
}
