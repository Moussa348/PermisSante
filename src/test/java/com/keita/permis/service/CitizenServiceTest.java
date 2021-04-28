package com.keita.permis.service;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.repository.CitizenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

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

    @Test
    void authentication(){
        //ARRANGE
        AuthForm authForm1 = new AuthForm("rejArch@gmail.com", "rejean123");
        Optional<Citizen> citizenOptional = Optional.of(Citizen.builder().email("rejArch@gmail.com").build());
        when(citizenRepository.findByEmailAndPassword(authForm1.getEmail(), authForm1.getPassword())).thenReturn(citizenOptional);

        AuthForm authForm2 = new AuthForm("andreMarc12@gmail.com", "andreee");
        when(citizenRepository.findByEmailAndPassword(authForm2.getEmail(), authForm2.getPassword())).thenReturn(Optional.empty());

        AuthForm authForm3 = new AuthForm("fanafan@gmail.com", "fanfan123");
        Optional<Citizen> citizenOptional2 = Optional.of(Citizen.builder().email("fanafan@gmail.com").build());
        citizenOptional2.get().setActive(false);
        when(citizenRepository.findByEmailAndPassword(authForm3.getEmail(), authForm3.getPassword())).thenReturn(Optional.empty());

        //ACT
        String emailReturned = citizenService.authentication(authForm1);
        String emailNotReturnedUserNonExistent = citizenService.authentication(authForm2);
        String emailNotReturnedNotUserNotActive = citizenService.authentication(authForm2);

        //ASSERT
        assertEquals(emailReturned,authForm1.getEmail());
        assertEquals(emailNotReturnedUserNonExistent,"");
        assertEquals(emailNotReturnedNotUserNotActive,"");
    }
    @Test
    void registration() {
        //ARRANGE
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
        citizen2.setParent(Citizen.builder().socialInsurance("454676424").build());
        when(citizenRepository.existsByEmail(citizen2.getEmail())).thenReturn(false);
        when(citizenRepository.findBySocialInsurance(citizen2.getParent().getSocialInsurance())).thenReturn(Optional.of(new Citizen()));

        Citizen citizen3 =
                Citizen.builder()
                        .email("moukFa@gmail.com").dateOfBirth(LocalDate.of(1996,12,23)).build();
        when(citizenRepository.existsByEmail(citizen3.getEmail())).thenReturn(true);

        Citizen citizen4 =
                Citizen.builder()
                        .email("tazzz@gmail.com")
                        .dateOfBirth(LocalDate.of(2009,02,03)).build();
        citizen4.setParent(Citizen.builder().socialInsurance("3243243243").build());
        when(citizenRepository.existsByEmail(citizen4.getEmail())).thenReturn(false);
        when(citizenRepository.findBySocialInsurance(citizen4.getParent().getSocialInsurance())).thenReturn(Optional.empty());

        when(citizenRepository.save(any(Citizen.class))).thenReturn(new Citizen());
        when(environment.getProperty("age.min")).thenReturn("18");

        //ACT
        int adultCitizenIsRegistered = citizenService.registration(citizen1);
        int kidCitizenIsRegistered = citizenService.registration(citizen2);
        int adultIsNotRegisteredAccountAlreadyExist = citizenService.registration(citizen3);
        int kidIsNotRegisteredParentAccountDoNotExist = citizenService.registration(citizen4);

        //ASSERT
        assertEquals(0,adultCitizenIsRegistered);
        assertEquals(0,kidCitizenIsRegistered);
        assertEquals(1,adultIsNotRegisteredAccountAlreadyExist);
        assertEquals(-1,kidIsNotRegisteredParentAccountDoNotExist);
    }

    @Test
    void viewCitizenInfo(){
        //ARRANGE
        String email1 = "citizen1@gmail.com";
        when(citizenRepository.findByEmail(email1)).thenReturn(Optional.of(new Citizen()));

        String email2 = "citizen2@gmail.com";
        when(citizenRepository.findByEmail(email2)).thenReturn(Optional.empty());

        //ACT
        Citizen citizenExist = citizenService.viewCitizenInfo(email1);
        Citizen citizenDoNotExist = citizenService.viewCitizenInfo(email2);

        //ASSERT
        assertNotNull(citizenExist);
        assertNull(citizenDoNotExist);
    }
}
