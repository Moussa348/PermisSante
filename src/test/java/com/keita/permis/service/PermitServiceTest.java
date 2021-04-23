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
        RequestPermitForm form1 = RequestPermitForm.builder()
                .email("AurelieLaflamme@gmail.com").password("aurelie123")
                .city("Laval").cellNumber("5143278654").build();
        Optional<Citizen> optionalCitizenForForm1 = Optional.of(
                Citizen.builder()
                        .firstName("Aurelie")
                        .lastName("Laflamme")
                        .socialInsurance("21313123")
                        .email("AurelieLaflamme@gmail.com")
                        .password("aurelie123")
                        .city("Laval").cellNumber("5143278654")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build()
        );
        when(citizenRepository.findByEmailAndPasswordAndCellNumberAndCity(
                form1.getEmail(), form1.getPassword(), form1.getCellNumber(), form1.getCity()
        )).thenReturn(optionalCitizenForForm1);
        when(permitRepository.findByActiveTrueAndCitizenEmail(form1.getEmail())).thenReturn(Optional.empty());
        when(permitRepository.countByCitizenEmail(form1.getEmail())).thenReturn(5);

        RequestPermitForm form2 = RequestPermitForm.builder()
                .email("saukeUchiha@gmail.com").password("chidori123")
                .city("Konoha").cellNumber("5143278654").build();
        Optional<Citizen> optionalCitizenForForm2 = Optional.of(
                Citizen.builder()
                        .firstName("Sasuke")
                        .lastName("Uchiha")
                        .socialInsurance("21313123")
                        .email("saukeUchiha@gmail.com")
                        .password("chidori123")
                        .city("Konoha").cellNumber("5143278654")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build()
        );
        when(citizenRepository.findByEmailAndPasswordAndCellNumberAndCity(
                form2.getEmail(), form2.getPassword(), form2.getCellNumber(), form2.getCity()
        )).thenReturn(optionalCitizenForForm2);
        when(permitRepository.findByActiveTrueAndCitizenEmail(form2.getEmail())).thenReturn(Optional.of(new Permit()));
        when(permitRepository.countByCitizenEmail(form2.getEmail())).thenReturn(5);

        RequestPermitForm form3 = RequestPermitForm.builder()
                .email("mikeTyson@gmail.com").password("mike123")
                .city("United-States").cellNumber("5143278654").build();
        Optional<Citizen> optionalCitizenForForm3 = Optional.of(
                Citizen.builder()
                        .firstName("Mike")
                        .lastName("Tyson")
                        .socialInsurance("21313123")
                        .email("mikeTyson@gmail.com")
                        .password("mike123")
                        .city("United-States").cellNumber("5143278654")
                        .dateOfBirth(LocalDate.of(1996, 12, 23)).build()
        );
        when(citizenRepository.findByEmailAndPasswordAndCellNumberAndCity(
                form3.getEmail(), form3.getPassword(), form3.getCellNumber(), form3.getCity()
        )).thenReturn(optionalCitizenForForm3);
        when(permitRepository.findByActiveTrueAndCitizenEmail(form3.getEmail())).thenReturn(Optional.empty());
        when(permitRepository.countByCitizenEmail(form3.getEmail())).thenReturn(0);

        RequestPermitForm form4 = RequestPermitForm.builder()
                .email("fafafafa@gmail.com").password("fafa")
                .city("Fafafa").cellNumber("fafafaf").build();
        when(citizenRepository.findByEmailAndPasswordAndCellNumberAndCity(
                form4.getEmail(), form4.getPassword(), form4.getCellNumber(), form4.getCity()
        )).thenReturn(Optional.empty());

        //ACT
        boolean renewPermitOfForm1 = permitService.renewPermit(form1);
        boolean cantRenewPermitOfForm2 = permitService.renewPermit(form2);
        boolean cantRenewPermitOfForm3 = permitService.renewPermit(form3);
        boolean cantRenewPermitOfForm4 = permitService.renewPermit(form4);

        //ASSERT
        assertTrue(renewPermitOfForm1);
        assertFalse(cantRenewPermitOfForm2);
        assertFalse(cantRenewPermitOfForm3);
        assertFalse(cantRenewPermitOfForm4);
    }
}
