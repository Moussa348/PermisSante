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

@DataJpaTest(properties = "spring.datasource.initialization-mode=never")//Désactivé,car il insert les donnees de mon data.sql
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
                        .restrictedAreas("NONE").build(),
                Permit.builder()
                        .citizen(citizenRepository.getOne(3L))
                        .permitCategory(PermitCategory.ADULT)
                        .permitType(PermitType.TEST)
                        .restrictedAreas("NONE").build()

        );
        permits.get(2).setActive(false);
        permits.get(3).setActive(false);
        permitRepository.saveAll(permits);
    }

    @Test
    void findByActiveTrueAndCitizenEmail(){
        //ARRANGE
        Citizen citizen1 = Citizen.builder().email("andreMarc12@gmail.com").build();
        Citizen citizen2 = Citizen.builder().email("jackDaniels@gmail.com").build();

        //ACT
        Optional<Permit> citizenHasPermitActive = permitRepository
                .findByActiveTrueAndCitizenEmail(citizen1.getEmail());

        Optional<Permit> citizenHasPermitNotActive = permitRepository
                .findByActiveTrueAndCitizenEmail(citizen2.getEmail());

        //ASSERT
        assertTrue(citizenHasPermitActive.isPresent());
        assertFalse(citizenHasPermitNotActive.isPresent());
    }

    @Test
    void countByCitizenEmail(){
        //ARRANGE
        Citizen citizen1 = Citizen.builder().email("jackDaniels@gmail.com").build();
        Citizen citizen2 = Citizen.builder().email("massou@gmail.com").build();

        //ACT
        int nbrPermitCitizen1 = permitRepository.countByCitizenEmail(citizen1.getEmail());
        int nbrPermitCitizen2 = permitRepository.countByCitizenEmail(citizen2.getEmail());

        //ASSERT
        assertEquals(nbrPermitCitizen1,2);
        assertEquals(nbrPermitCitizen2,0);
    }

    @Test
    void getByExpirationDateBefore(){
        //ARRANGE
        LocalDate date1 = LocalDate.of(2021,11,23);
        LocalDate date2 = LocalDate.now();

        //ACT
        List<Permit> permitsExceedingDate1 = permitRepository.getByExpirationDateBefore(date1);
        List<Permit> permitsExceedingDate2 = permitRepository.getByExpirationDateBefore(date2);

        //ASSERT
        assertEquals(4,permitsExceedingDate1.size());
        assertEquals(0,permitsExceedingDate2.size());
    }

    @Test
    void disablePermit(){
        //ARRANGE
        LocalDate expirationDate1 = LocalDate.of(2021,05,10);
        LocalDate expirationDate2 = LocalDate.now();

        //ACT
        int nbOfUpdates1 = permitRepository.disablePermit(expirationDate1);
        int nbOfUpdates2 = permitRepository.disablePermit(expirationDate2);
        //ASSERT
        assertEquals(2,nbOfUpdates1);
        assertEquals(0,nbOfUpdates2);
    }


}
