package com.keita.permis.service;

import com.keita.permis.model.Citizen;
import com.keita.permis.repository.PermitRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PermitServiceTest {

    @Mock
    PermitRepository permitRepository;

    @InjectMocks
    PermitService permitService;

    @Test
    void generatePermit() {
        //Arrange
        Citizen citizen1 = Citizen.builder()
                .firstName("Rejean")
                .lastName("Archambault")
                .gender("M")
                .email("rejArch@gmail.com")
                .password("rej123")
                .cellNumber("5143435478")
                .city("Trois-Rivieres")
                .dateOfBirth(LocalDate.of(1996, 11, 2))
                .socialInsurance("ARCA96110214").build();
        //Act
        boolean successful = permitService.generatePermit(citizen1);
        //Assert
        assertTrue(successful);
    }
/*

    @Test
    void generatePDF(){
        //Arrange
        Citizen citizen1 = Citizen.builder()
                .firstName("Rejean")
                .lastName("Archambault")
                .gender("M")
                .email("rejArch@gmail.com")
                .password("rej123")
                .cellNumber("5143435478")
                .city("Trois-Rivieres")
                .dateOfBirth(LocalDate.of(1996, 11, 2))
                .socialInsurance("ARCA96110214").build();
        //Act
        boolean success = permitService.generatePDF(citizen1);
        //Assert
        assertTrue(success);

    }
 */
}
