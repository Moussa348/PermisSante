package com.keita.permis.service;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.dto.SubmitForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.repository.CitizenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CitizenServiceTest {

    @Mock
    CitizenRepository citizenRepository;

    @Mock
    Environment environment;

    @InjectMocks
    CitizenService citizenService;

    //TODO : add improbable test (TP3)
    @Test
    void authentication(){
        //ARRANGE
        AuthForm authForm1 = new AuthForm("rejArch@gmail.com", "rej123");
        Optional<Citizen> citizenOptional = Optional.of(Citizen.builder().email("rejArch@gmail.com").build());
        when(citizenRepository.findByEmailAndPassword(authForm1.getEmail(), authForm1.getPassword())).thenReturn(citizenOptional);

        AuthForm authForm2 = new AuthForm("andreMarc12@gmail.com", "andreee");
        when(citizenRepository.findByEmailAndPassword(authForm2.getEmail(), authForm2.getPassword())).thenReturn(Optional.empty());

        //ACT
        String emailReturned = citizenService.authentication(authForm1);
        String emailNotReturned = citizenService.authentication(authForm2);

        //ASSERT
        assertEquals(emailReturned,authForm1.getEmail());
        assertEquals(emailNotReturned,"");
    }
    @Test
    void registration() {
        //Arrange
        Citizen citizen1 =
                Citizen.builder()
                        .email("kMihoubi@gmail.com")
                        .dateOfBirth(LocalDate.of(1981,01,01))
                        .build();
        when(citizenRepository.existsByEmail(citizen1.getEmail())).thenReturn(false);



        Citizen citizen2 =
                Citizen.builder()
                      .email("mikaKami@gmail.com")
                        .dateOfBirth(LocalDate.of(2009,02,03)).build();
        citizen2.setParent(Citizen.builder().email("massou@gmail.com").build());
        when(citizenRepository.existsByEmail(citizen2.getEmail())).thenReturn(false);
        when(citizenRepository.findByEmail(citizen2.getParent().getEmail())).thenReturn(Optional.of(new Citizen()));




        Citizen citizen3 =
                Citizen.builder()
                        .email("moukFa@gmail.com").dateOfBirth(LocalDate.of(1996,12,23)).build();
        when(citizenRepository.existsByEmail(citizen3.getEmail())).thenReturn(true);

        Citizen citizen4 =
                Citizen.builder()
                        .email("tazzz@gmail.com")
                        .dateOfBirth(LocalDate.of(2009,02,03)).build();
        citizen4.setParent(Citizen.builder().email("marc@gmail.com").build());
        when(citizenRepository.existsByEmail(citizen4.getEmail())).thenReturn(false);
        when(citizenRepository.findByEmail(citizen4.getParent().getEmail())).thenReturn(Optional.empty());

        when(citizenRepository.save(any(Citizen.class))).thenReturn(new Citizen());
        when(environment.getProperty("age.min")).thenReturn("18");

        //Act

        boolean adultCitizenIsRegistered = citizenService.registration(citizen1);
        boolean kidCitizenIsRegistered = citizenService.registration(citizen2);
        boolean adultIsNotRegistered = citizenService.registration(citizen3);
        boolean kidIsNotRegistered = citizenService.registration(citizen4);
        //Assert
        assertTrue(adultCitizenIsRegistered);
        assertTrue(kidCitizenIsRegistered);
        assertFalse(adultIsNotRegistered);
        assertFalse(kidIsNotRegistered);
    }
}
