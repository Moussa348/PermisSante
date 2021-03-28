package com.keita.permis.service;

import com.keita.permis.dto.SubmitForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.Permit;
import com.keita.permis.repository.CitizenRepository;
import com.keita.permis.repository.PermitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PermitServiceTest {

    @Mock
    PermitRepository permitRepository;

    @Mock
    CitizenRepository citizenRepository;

    @Mock
    Environment environment;

    @InjectMocks
    PermitService permitService;

    @Test
    void generatePermit() throws Exception {
        //Arrange
        SubmitForm form1 = SubmitForm.builder().email("moukFa@gmail.com").password("fadi123").build();
        Optional<Citizen> optionalCitizenForForm1 = Optional.of(
                Citizen.builder()
                        .firstName("Fadi")
                        .lastName("Mouk")
                        .socialInsurance("21313123")
                        .email("moukFa@gmail.com")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build()
        );
        when(citizenRepository.findByEmailAndPassword(form1.getEmail(), form1.getPassword()))
                .thenReturn(optionalCitizenForForm1);
        when(permitRepository.findByActiveTrueAndCitizenEmail(form1.getEmail()))
                .thenReturn(Optional.empty());
        when(permitRepository.countByCitizenEmail(form1.getEmail())).thenReturn(0);
        when(permitRepository.save(any(Permit.class))).thenReturn(new Permit());


        SubmitForm form2 = SubmitForm.builder().email("rejArch@gmail.com").password("fadi123").build();
        Optional<Citizen> optionalCitizenForForm2 = Optional.of(
                Citizen.builder()
                        .firstName("Rejean")
                        .lastName("Archambault")
                        .socialInsurance("21313123")
                        .email("rejArch@gmail.com")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build()
        );
        when(citizenRepository.findByEmailAndPassword(form2.getEmail(), form2.getPassword()))
                .thenReturn(optionalCitizenForForm2);
        when(permitRepository.findByActiveTrueAndCitizenEmail(form2.getEmail()))
                .thenReturn(Optional.of(new Permit()));
        when(permitRepository.countByCitizenEmail(form2.getEmail())).thenReturn(1);


        SubmitForm form3 = SubmitForm.builder().email("mikaKami@gmail.com").password("mika123").build();
        Optional<Citizen> optionalCitizenForForm3 = Optional.of(
                Citizen.builder()
                        .firstName("Fadi")
                        .lastName("Mouk").email("mikaKami@gmail.com").password("mika123")
                        .socialInsurance("21313123").dateOfBirth(LocalDate.of(1991, 12, 23)).build()
        );
        when(citizenRepository.findByEmailAndPassword(form3.getEmail(), form3.getPassword()))
                .thenReturn(optionalCitizenForForm3);
        when(permitRepository.findByActiveTrueAndCitizenEmail(form3.getEmail()))
                .thenReturn(Optional.empty());
        when(permitRepository.countByCitizenEmail(form3.getEmail())).thenReturn(4);


        SubmitForm form4 = SubmitForm.builder().email("araaaaa@gmail.com").password("mika123").build();
        when(citizenRepository.findByEmailAndPassword(form4.getEmail(), form4.getPassword()))
                .thenReturn(Optional.empty());


        when(environment.getProperty("qr.directory"))
                .thenReturn("C:\\Users\\mansa\\Documents\\OneDrive\\Documents\\techniqueInformatique\\quatriemeSession\\spring-angular\\PermisSante\\barCode\\");
        when(environment.getProperty("pdf.directory"))
                .thenReturn("C:\\Users\\mansa\\Documents\\OneDrive\\Documents\\techniqueInformatique\\quatriemeSession\\spring-angular\\PermisSante\\pdf\\");
        when(environment.getProperty("qrcode.extension")).thenReturn(".PNG");
        when(environment.getProperty("pdf.extension")).thenReturn(".pdf");
        when(environment.getProperty("qrcode.format")).thenReturn("PNG");
        when(environment.getProperty("qrcode.dimension")).thenReturn("300");

        //Act
        boolean permitForForm1IsGenerated = permitService.generatePermit(form1);
        boolean permitForForm2IsGenerated = permitService.generatePermit(form2);
        boolean permitForForm3IsNotGenerated = permitService.generatePermit(form3);
        boolean permitForForm4IsNotGenerated = permitService.generatePermit(form4);

        //Assert
        assertTrue(permitForForm1IsGenerated);
        assertTrue(permitForForm2IsGenerated);
        assertFalse(permitForForm3IsNotGenerated);
        assertFalse(permitForForm4IsNotGenerated);
    }
}
