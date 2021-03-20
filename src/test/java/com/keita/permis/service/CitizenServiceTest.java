package com.keita.permis.service;

import com.keita.permis.dto.UserSubmitForm;
import com.keita.permis.model.Administrator;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.User;
import com.keita.permis.repository.CitizenRepository;
import org.junit.jupiter.api.Assertions;
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
                        .dateOfBirth(LocalDate.of(1996,12,23))
                        .socialInsurance("REJEAR1239892").city("Gaspesie").build(),
                Citizen.builder()
                        .firstName("Antoine").lastName("Fafard")
                        .email("fafaAn@gmail.com").password("ant123")
                        .cellNumber("5143456789").city("Laval")
                        .dateOfBirth(LocalDate.of(1996,12,23))
                        .socialInsurance("FAFAAN1232424").build(),
                Citizen.builder()
                        .firstName("Fadi").lastName("Mouk")
                        .email("moukFa@gmail.com").password("fadi123")
                        .cellNumber("5143456789").city("Montreal")
                        .dateOfBirth(LocalDate.of(1996,12,23))
                        .socialInsurance("FADIMO2321412").build()
        );

        citizenRepository.saveAll(citizens);
    }

    @Test
    void registration(){
        //Arrange
        UserSubmitForm form1 =
                UserSubmitForm.builder()
                        .firstName("Karim").lastName("Mihoubi")
                        .gender("M").email("kMihoubi@gmail.com").password("karim123")
                        .cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIHOUKa1234390").dateOfBirth("1976-02-01").build();

        UserSubmitForm form2 =
                UserSubmitForm.builder()
                        .email("briceNice@gmail.com").dateOfBirth("76-02-01").build();

        UserSubmitForm form3 =
                UserSubmitForm.builder()
                        .firstName("Karim").lastName("Mihoubi")
                        .gender("M").email("fafaAn@gmail.com").password("karim123")
                        .cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIHOUKa1234390").dateOfBirth("1976-02-01").build();

        //Act
        Mockito.when(citizenRepository.existsByEmail(form1.getEmail())).thenReturn(false);


        //Assert
        Assertions.assertTrue(citizenService.registration(form1));
    }


}
