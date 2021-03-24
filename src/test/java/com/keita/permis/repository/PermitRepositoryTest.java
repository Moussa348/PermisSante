package com.keita.permis.repository;

import com.keita.permis.enums.PermitCategory;
import com.keita.permis.enums.PermitType;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.Permit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PermitRepositoryTest {

    @Autowired
    CitizenRepository citizenRepository;

    @Autowired
    PermitRepository permitRepository;

    @BeforeAll
    public void insertData() {
        List<Citizen> citizens = Arrays.asList(
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
                        .socialInsurance("ANDM68091315").build(),
                Citizen.builder()
                        .firstName("Jack")
                        .lastName("Daniels")
                        .gender("M")
                        .email("jackDaniels@gmail.com")
                        .password("jack123")
                        .cellNumber("4509876549")
                        .city("Quebec")
                        .dateOfBirth(LocalDate.of(1968, 9, 13))
                        .socialInsurance("DANIJA1231233213").build()
        );
        citizenRepository.saveAll(citizens);

        List<Permit> permits = Arrays.asList(
                Permit.builder()
                        .citizen(citizenRepository.getOne(1L))
                        .permitCategory(PermitCategory.ADULT)
                        .permitType(PermitType.VACCINE)
                        .restrictedAreas("NONE").build(),
                Permit.builder()
                        .citizen(citizenRepository.getOne(2L))
                        .permitCategory(PermitCategory.ADULT)
                        .permitType(PermitType.VACCINE)
                        .restrictedAreas("NONE").build(),
                Permit.builder()
                        .citizen(citizenRepository.getOne(3L))
                        .permitCategory(PermitCategory.ADULT)
                        .permitType(PermitType.TEST)
                        .restrictedAreas("NONE").build()

        );
        permitRepository.saveAll(permits);
    }

    @Test
    void findByCitizenEmailAndCitizenPassword(){
        //Arrange
        Citizen citizen1 = Citizen.builder().email("andreMarc12@gmail.com").password("marc123").build();
        Citizen citizen2 = Citizen.builder().email("jeaaaan@gmail.com").password("raa").build();

        //Act
        Optional<Permit> citizen1HasPermit = permitRepository.findByCitizenEmailAndCitizenPassword(citizen1.getEmail(),citizen1.getPassword());
        Optional<Permit> citizen2HasPermit = permitRepository.findByCitizenEmailAndCitizenPassword(citizen2.getEmail(),citizen1.getPassword());

        //Assert
        assertTrue(citizen1HasPermit.isPresent());
        assertFalse(citizen2HasPermit.isPresent());
    }

    @Test
    void findByCitizenEmailAndCitizenCellNumberAndCitizenCity(){
        //Arrange
        Citizen citizen1 = Citizen.builder()
                .email("rejArch@gmail.com").cellNumber("5143435478").city("Trois-Rivieres").build();
        Citizen citizen2 = Citizen.builder()
                .email("rejArch@gmail.com").cellNumber("5143435478").city("Trois-Riviere").build();
        //Act
        Optional<Permit> citizenHasPermit = permitRepository
                .findByCitizenEmailAndCitizenCellNumberAndCitizenCity(
                        citizen1.getEmail(),
                        citizen1.getCellNumber(),
                        citizen1.getCity()
                );
        Optional<Permit> citizenHasNotPermit = permitRepository
                .findByCitizenEmailAndCitizenCellNumberAndCitizenCity(
                        citizen2.getEmail(),
                        citizen2.getCellNumber(),
                        citizen2.getCity()
                );
        //Assert
        assertTrue(citizenHasPermit.isPresent());
        assertFalse(citizenHasNotPermit.isPresent());
    }

    @Test
    void findByCitizenEmailAndActiveTrue(){
        //Arrange
        Citizen citizen1 = Citizen.builder().email("andreMarc12@gmail.com").build();
        Citizen citizen2 = Citizen.builder().email("jackDaniels@gmail.com").build();

        //Act
        Optional<Permit> citizenHasPermitNotExpired = permitRepository
                .findByCitizenEmailAndExpirationDateAfter(citizen1.getEmail(),LocalDate.of(2021,7,24));

        Optional<Permit> citizenHasPermitExpired = permitRepository
                .findByCitizenEmailAndExpirationDateAfter(citizen2.getEmail(),LocalDate.of(2022,1,1));

        //Assert
        assertTrue(citizenHasPermitNotExpired.isPresent());
        assertFalse(citizenHasPermitExpired.isPresent());

    }

}
