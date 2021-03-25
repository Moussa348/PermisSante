package com.keita.permis.service;

import com.keita.permis.dto.SubmitForm;
import com.keita.permis.enums.PermitCategory;
import com.keita.permis.enums.PermitType;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.Permit;
import com.keita.permis.repository.CitizenRepository;
import com.keita.permis.repository.PermitRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PermitServiceTest {

    @Mock
    PermitRepository permitRepository;

    @Mock
    CitizenRepository citizenRepository;

    @InjectMocks
    PermitService permitService;

    @BeforeAll
    void insertData(){
        List<Citizen> citizens = Arrays.asList(
                Citizen.builder()
                        .firstName("Rejean").lastName("Archambault")
                        .email("rejArch@gmail.com").password("rej123")
                        .cellNumber("5143456789").city("Gaspesie")
                        .dateOfBirth(LocalDate.of(1967, 12, 23))
                        .socialInsurance("REJEAR1239892").city("Gaspesie").build(),
                Citizen.builder()
                        .firstName("Antoine").lastName("Fafard")
                        .email("fafaAn@gmail.com").password("ant123")
                        .cellNumber("5143456789").city("Laval")
                        .dateOfBirth(LocalDate.of(1996, 12, 23))
                        .socialInsurance("FAFAAN1232424").build(),
                Citizen.builder()
                        .firstName("Fadi").lastName("Mouk")
                        .email("moukFa@gmail.com").password("fadi123")
                        .cellNumber("5143456789").city("Montreal")
                        .dateOfBirth(LocalDate.of(1996, 12, 23))
                        .socialInsurance("FADIMO2321412").build()
        );
        citizenRepository.saveAll(citizens);

        Citizen child =
                Citizen.builder()
                        .firstName("Mika").lastName("Kami")
                        .gender("F").email("mikaKami@gmail.com").password("mika123")
                        .cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIKA45678765").dateOfBirth(LocalDate.of(2010,12,23))
                        .parent(citizenRepository.getOne(1L)).build();

        citizenRepository.save(child);

        List<Permit> permits = Arrays.asList(
                Permit.builder()
                        .citizen(citizenRepository.getOne(1L))
                        .permitCategory(PermitCategory.ADULT)
                        .permitType(PermitType.VACCINE)
                        .restrictedAreas("NONE").build(),
                Permit.builder()
                        .citizen(citizenRepository.getOne(2L))
                        .permitCategory(PermitCategory.ADULT)
                        .permitType(PermitType.TEST)
                        .restrictedAreas("Crowded Space,Indoor events").build(),
                Permit.builder()
                        .citizen(citizenRepository.getOne(4L))
                        .permitCategory(PermitCategory.LITTLE_KID)
                        .permitType(PermitType.VACCINE)
                        .restrictedAreas("NONE").build()
        );

        permits.get(2).setActive(false);

        permitRepository.saveAll(permits);
    }

    @Test
    void generatePermit() {
        //Arrange
        SubmitForm form1 = SubmitForm.builder().email("moukFa@gmail.com").password("fadi123").build();
        Optional<Citizen> optCitizenForForm1 = Optional.of(
                Citizen.builder()
                        .email("moukFa@gmail.com").password("fadi123")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build()
        );

        SubmitForm form2 = SubmitForm.builder().email("rejArch@gmail.com").password("fadi123").build();

        SubmitForm form3 = SubmitForm.builder().email("mikaKami@gmail.com").password("mika123").build();
        SubmitForm form4 = SubmitForm.builder().email("araaaaa@gmail.com").password("mika123").build();


        //Act
        when(citizenRepository.findByEmailAndPassword(form1.getEmail(), form1.getPassword()))
                .thenReturn(optCitizenForForm1);
        when(permitRepository.findByActiveTrueAndCitizenEmail(form1.getEmail()))
                .thenReturn(Optional.empty());
        when(permitRepository.countByCitizenEmail(form1.getEmail())).thenReturn(0);


        when(citizenRepository.findByEmailAndPassword(form2.getEmail(), form2.getPassword()))
                .thenReturn(Optional.of(new Citizen()));
        when(permitRepository.findByActiveTrueAndCitizenEmail(form2.getEmail()))
                .thenReturn(Optional.of(new Permit()));
        when(permitRepository.countByCitizenEmail(form2.getEmail())).thenReturn(1);


        when(citizenRepository.findByEmailAndPassword(form3.getEmail(), form3.getPassword()))
                .thenReturn(optCitizenForForm1);
        when(permitRepository.findByActiveTrueAndCitizenEmail(form3.getEmail()))
                .thenReturn(Optional.empty());
        when(permitRepository.countByCitizenEmail(form3.getEmail())).thenReturn(1);

        when(citizenRepository.findByEmailAndPassword(form4.getEmail(), form4.getPassword()))
                .thenReturn(Optional.empty());

        when(permitRepository.save(any(Permit.class))).thenReturn(new Permit());



        //Assert
        assertTrue(permitService.generatePermit(form1));
        assertTrue(permitService.generatePermit(form2));

        assertFalse(permitService.generatePermit(form3));
        assertFalse(permitService.generatePermit(form4));
    }
}
