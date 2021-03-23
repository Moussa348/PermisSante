package com.keita.permis.service;

import com.keita.permis.dto.SubmitForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.repository.CitizenRepository;
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
import static org.mockito.Mockito.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CitizenServiceTest {

    @Mock
    CitizenRepository citizenRepository;

    @InjectMocks
    CitizenService citizenService;


    @BeforeAll
    public void insertData() {
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
    }

    @Test
    void registration() {
        //Arrange
        SubmitForm form1 =
                SubmitForm.builder()
                        .firstName("Karim").lastName("Mihoubi")
                        .gender("M").email("kMihoubi@gmail.com").password("karim123")
                        .passwordAgain("karim123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIHOUKa1234390").dateOfBirth("1976-02-01").build();

        SubmitForm form2 =
                SubmitForm.builder()
                        .firstName("Mika").lastName("Kami")
                        .gender("F").email("mikaKami@gmail.com").password("mika123")
                        .passwordAgain("mika123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIKA45678765").dateOfBirth("2010-12-23")
                        .firstNameParent("Rejean").lastNameParent("Archambault").emailParent("rejArch@gmail.com").build();

        SubmitForm form3 =
                SubmitForm.builder()
                        .email("mikaKami@gmail.com").dateOfBirth("54-12-12").build();

        SubmitForm form4 =
                SubmitForm.builder()
                        .email("moukFa@gmail.com").dateOfBirth("1996-12-23").build();

        SubmitForm form5 =
                SubmitForm.builder()
                        .firstName("Mika").lastName("Kami")
                        .gender("F").email("mikaKami@gmail.com").password("mika123")
                        .passwordAgain("mika123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIKA45678765").dateOfBirth("2004-12-23")
                        .firstNameParent("incognito").lastNameParent("incognito").emailParent("incognito@gmail.com").build();

        SubmitForm form6 =
                SubmitForm.builder()
                        .firstName("Mathieu").lastName("Marc")
                        .gender("F").email("mathieuMa@gmail.com").password("mathieu123")
                        .passwordAgain("mathieu1234").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIKA45678765").dateOfBirth("1994-12-24").build();


        //Act
        when(citizenRepository.existsByEmail(form1.getEmail())).thenReturn(false);

        when(citizenRepository.existsByEmail(form2.getEmail())).thenReturn(false);
        when(citizenRepository.existsByEmailAndFirstNameAndLastName(
                form2.getEmailParent(), form2.getFirstNameParent(), form2.getLastNameParent()))
                .thenReturn(true);
        when(citizenRepository.findByFirstNameAndLastNameAndEmail(
                form2.getFirstNameParent(), form2.getLastNameParent(), form2.getEmailParent()))
                .thenReturn(Optional.of(Citizen.builder().build()));

        when(citizenRepository.existsByEmail(form4.getEmail())).thenReturn(true);

        when(citizenRepository.existsByEmail(form5.getEmail())).thenReturn(false);
        when(citizenRepository.existsByEmailAndFirstNameAndLastName(
                form5.getEmailParent(), form5.getFirstNameParent(), form5.getLastNameParent()))
                .thenReturn(false);
        when(citizenRepository.findByFirstNameAndLastNameAndEmail(
                form5.getEmailParent(), form5.getFirstNameParent(), form5.getLastNameParent()))
                .thenReturn(Optional.empty());

        when(citizenRepository.existsByEmail(form6.getEmail())).thenReturn(false);

        when(citizenRepository.save(any(Citizen.class))).thenReturn(Citizen.builder().build());

        //Assert
        assertTrue(citizenService.registration(form1));
        assertTrue(citizenService.registration(form2));

        assertFalse(citizenService.registration(form3));
        assertFalse(citizenService.registration(form4));
        assertFalse(citizenService.registration(form5));
        assertFalse(citizenService.registration(form6));
    }


}
