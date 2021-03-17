package com.keita.permis.repository;

import com.keita.permis.enums.PermitCategory;
import com.keita.permis.enums.PermitType;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.Permit;
import org.junit.jupiter.api.Assertions;
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
                        .socialInsurance("ANDM68091315").build()
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
                        .restrictedAreas("NONE").build()

        );
        permitRepository.saveAll(permits);
    }

    @Test
    void findByCitizenEmail(){
        //Arrange
        Citizen citizen1 = Citizen.builder().email("andreMarc12@gmail.com").build();
        Citizen citizen2 = Citizen.builder().email("jeaaaan@gmail.com").build();

        //Act
        Optional<Permit> citizen1HasPermit = permitRepository.findByCitizenEmail(citizen1.getEmail());
        Optional<Permit> citizen2HasPermit = permitRepository.findByCitizenEmail(citizen2.getEmail());

        //Assert
        assertTrue(citizen1HasPermit.isPresent());
        assertFalse(citizen2HasPermit.isPresent());
    }

}
