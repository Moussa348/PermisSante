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

    @Mock
    RestTemplate restTemplate;

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
        SubmitForm form1 =
                SubmitForm.builder()
                        .firstName("Karim").lastName("Mihoubi")
                        .gender("M").email("kMihoubi@gmail.com").password("karim123")
                        .passwordAgain("karim123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIHOUKa1234390").dateOfBirth("1976-02-01").build();
        form1.setTypePermit("TEST");
        when(citizenRepository.existsByEmail(form1.getEmail())).thenReturn(false);


        SubmitForm form2 =
                SubmitForm.builder()
                        .firstName("Mika").lastName("Kami")
                        .gender("F").email("mikaKami@gmail.com").password("mika123")
                        .passwordAgain("mika123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIKA45678765").dateOfBirth("2010-12-23")
                        .firstNameParent("Rejean").lastNameParent("Archambault").emailParent("rejArch@gmail.com").build();
        form2.setTypePermit("TEST");
        Optional<Citizen> parent = Optional.of(
                Citizen.builder()
                        .firstName("Rejean")
                        .lastName("Kami")
                        .socialInsurance("21313123")
                        .email("rejArch@gmail.com")
                        .dateOfBirth(LocalDate.of(1967, 12, 23)).build()
        );
        when(citizenRepository.existsByEmail(form2.getEmail())).thenReturn(false);
        when(citizenRepository.findByEmailAndFirstNameAndLastName(
                form2.getEmailParent(), form2.getFirstNameParent(), form2.getLastNameParent()))
                .thenReturn(parent);

        when(citizenRepository.save(any(Citizen.class))).thenReturn(new Citizen());


        SubmitForm form3 =
                SubmitForm.builder()
                        .email("mikaKami@gmail.com").dateOfBirth("54-12-12").build();


        SubmitForm form4 =
                SubmitForm.builder()
                        .email("moukFa@gmail.com").dateOfBirth("1996-12-23").build();
        when(citizenRepository.existsByEmail(form4.getEmail())).thenReturn(true);


        SubmitForm form5 =
                SubmitForm.builder()
                        .firstName("Mika").lastName("Kami")
                        .gender("F").email("mikaKami@gmail.com").password("mika123")
                        .passwordAgain("mika123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIKA45678765").dateOfBirth("2004-12-23")
                        .firstNameParent("incognito").lastNameParent("incognito").emailParent("incognito@gmail.com").build();
        when(citizenRepository.existsByEmail(form5.getEmail())).thenReturn(false);
        when(citizenRepository.findByEmailAndFirstNameAndLastName(
                form5.getEmailParent(), form5.getFirstNameParent(), form5.getLastNameParent()))
                .thenReturn(Optional.empty());


        SubmitForm form6 =
                SubmitForm.builder()
                        .firstName("Mathieu").lastName("Marc")
                        .gender("F").email("mathieuMa@gmail.com").password("mathieu123")
                        .passwordAgain("mathieuuu1234").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIKA45678765").dateOfBirth("1994-12-24").build();
        when(citizenRepository.existsByEmail(form6.getEmail())).thenReturn(false);
        when(citizenRepository.findByEmailAndFirstNameAndLastName(
                form6.getEmailParent(), form6.getFirstNameParent(), form6.getLastNameParent()))
                .thenReturn(Optional.empty());

        when(environment.getProperty("age.min")).thenReturn("18");

        //Act
        boolean citizenFromForm1IsRegistered = citizenService.registration(form1);
        boolean citizenFromForm2IsRegistered = citizenService.registration(form2);
        boolean citizenFromForm3IsNotRegistered = citizenService.registration(form3);
        boolean citizenFromForm4IsNotRegistered = citizenService.registration(form4);
        boolean citizenFromForm5IsNotRegistered = citizenService.registration(form5);
        boolean citizenFromForm6IsNotRegistered = citizenService.registration(form6);

        //Assert
        assertTrue(citizenFromForm1IsRegistered);
        assertTrue(citizenFromForm2IsRegistered);
        assertFalse(citizenFromForm3IsNotRegistered);
        assertFalse(citizenFromForm4IsNotRegistered);
        assertFalse(citizenFromForm5IsNotRegistered);
        assertFalse(citizenFromForm6IsNotRegistered);
    }

    @Test
    void getPermitTypeIfInhabitantIsValidWithSocialInsurance(){
        //ARRANGE
        String socialInsurance1 = "21321314";
        when(restTemplate.getForEntity("http://localhost:9093/ministry/searchForRegistration/" + socialInsurance1,String.class))
                .thenReturn(new ResponseEntity(socialInsurance1, HttpStatus.OK));
        String socialInsurance2 = "43242442";

        //when(environment.getProperty("api.url.registration")).thenReturn("http://localhost:9093/ministry/searchForRegistration/");
        //ACT
        String gettingSocialInsurance = citizenService.getPermitTypeIfInhabitantIsValidWithSocialInsurance(socialInsurance1);
        //String notGettingSocialInsurance
        //ASSERT
        assertEquals(gettingSocialInsurance,socialInsurance1);

    }


}
