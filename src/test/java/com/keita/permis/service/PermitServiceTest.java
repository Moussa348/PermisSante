package com.keita.permis.service;

import com.keita.permis.dto.RequestPermitForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.Permit;
import com.keita.permis.repository.CitizenRepository;
import com.keita.permis.repository.PermitRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
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

    //TODO : learn Mockito @Spy

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    PermitService permitService;

    @BeforeEach
    void mockingEnvironmentPropertyAndJavaMailSender() {
        //il va falloir mettre le directory ou vous voulez que ca soit générer
        when(environment.getProperty("qr.directory"))
                .thenReturn("C:\\Users\\mansa\\Documents\\OneDrive\\Documents\\techniqueInformatique\\quatriemeSession\\PermisSante\\barCode\\");
        when(environment.getProperty("pdf.directory"))
                .thenReturn("C:\\Users\\mansa\\Documents\\OneDrive\\Documents\\techniqueInformatique\\quatriemeSession\\PermisSante\\pdf\\");
        when(environment.getProperty("qrcode.extension")).thenReturn(".PNG");
        when(environment.getProperty("pdf.extension")).thenReturn(".pdf");
        when(environment.getProperty("qrcode.format")).thenReturn("PNG");
        when(environment.getProperty("qrcode.dimension")).thenReturn("300");
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        when(environment.getProperty("age.min")).thenReturn("18");
    }

    //TODO : Test improbable situations (TP3)

    @Test
    void generatePermit() throws Exception {
        //ARRANGE
        String email1 = "moukFa@gmail.com";
        Optional<Citizen> optionalCitizenForForm1 = Optional.of(
                Citizen.builder()
                        .email(email1)
                        .socialInsurance("132131231")
                        .lastName("Fadi")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build()
        );
        when(citizenRepository.findByEmail(email1)).thenReturn(optionalCitizenForForm1);
        when(permitRepository.findByActiveTrueAndCitizenEmail(email1)).thenReturn(Optional.empty());
        when(permitRepository.countByCitizenEmail(email1)).thenReturn(0);
        when(permitRepository.save(any(Permit.class))).thenReturn(new Permit());

        String email2 = "rejArch@gmail.com";
        Optional<Citizen> optionalCitizenForForm2 = Optional.of(
                Citizen.builder()
                        .lastName("Archambault")
                        .socialInsurance("64646456")
                        .email("rejArch@gmail.com")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build()
        );
        when(citizenRepository.findByEmail(email2)).thenReturn(optionalCitizenForForm2);
        when(permitRepository.findByActiveTrueAndCitizenEmail(email2)).thenReturn(Optional.of(new Permit()));
        when(permitRepository.countByCitizenEmail(email2)).thenReturn(2);

        String email3 = "mikaKami@gmail.com";
        Optional<Citizen> optionalCitizenForForm3 = Optional.of(
                Citizen.builder()
                        .lastName("Mouk").email("mikaKami@gmail.com")
                        .socialInsurance("21313123").dateOfBirth(LocalDate.of(1991, 12, 23)).build()
        );
        when(citizenRepository.findByEmail(email3)).thenReturn(optionalCitizenForForm3);
        when(permitRepository.findByActiveTrueAndCitizenEmail(email3)).thenReturn(Optional.empty());
        when(permitRepository.countByCitizenEmail(email3)).thenReturn(4);

        String email4 = "araaaaa@gmail.com";
        when(citizenRepository.findByEmail(email4)).thenReturn(Optional.empty());

        //ACT
        boolean permitGeneratedForUserThatHaveNoPermit = permitService.generatePermit(email1);
        boolean permitGeneratedForUserThatHaveOnePermitActive = permitService.generatePermit(email2);
        boolean permitNotGeneratedForUserThatHavePermitsAndNoneActive = permitService.generatePermit(email3);
        boolean permitNotGeneratedForUserThatDoNotExist = permitService.generatePermit(email4);

        //ASSERT
        assertTrue(permitGeneratedForUserThatHaveNoPermit);
        assertTrue(permitGeneratedForUserThatHaveOnePermitActive);
        assertFalse(permitNotGeneratedForUserThatHavePermitsAndNoneActive);
        assertFalse(permitNotGeneratedForUserThatDoNotExist);
    }

    @Test
    void renewPermit() throws Exception {
        //ARRANGE
        Citizen citizen1 =
                Citizen.builder()
                        .lastName("Laflamme")
                        .socialInsurance("21313123")
                        .email("AurelieLaflamme@gmail.com")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build();
        when(permitRepository.findByActiveTrueAndCitizenEmail(citizen1.getEmail())).thenReturn(Optional.empty());
        when(permitRepository.countByCitizenEmail(citizen1.getEmail())).thenReturn(5);

        Citizen citizen2 =
                Citizen.builder()
                        .lastName("Uchiha")
                        .socialInsurance("21313123")
                        .email("saukeUchiha@gmail.com")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build();
        when(permitRepository.findByActiveTrueAndCitizenEmail(citizen2.getEmail())).thenReturn(Optional.of(new Permit()));
        when(permitRepository.countByCitizenEmail(citizen2.getEmail())).thenReturn(5);

        Citizen citizen3 =
                Citizen.builder()
                        .lastName("Tyson")
                        .socialInsurance("21313123")
                        .email("mikeTyson@gmail.com")
                        .password("mike123")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build();
        when(permitRepository.findByActiveTrueAndCitizenEmail(citizen3.getEmail())).thenReturn(Optional.empty());
        when(permitRepository.countByCitizenEmail(citizen3.getEmail())).thenReturn(0);

        //ACT
        boolean renewPermitOfForm1 = permitService.renewPermit(citizen1);
        boolean cantRenewPermitOfForm2 = permitService.renewPermit(citizen2);
        boolean cantRenewPermitOfForm3 = permitService.renewPermit(citizen3);

        //ASSERT
        assertTrue(renewPermitOfForm1);
        assertFalse(cantRenewPermitOfForm2);
        assertFalse(cantRenewPermitOfForm3);
    }
}
