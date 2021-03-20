package com.keita.permis.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.keita.permis.enums.PermitCategory;
import com.keita.permis.enums.PermitType;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.Permit;
import com.keita.permis.repository.PermitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
    void generateQR() throws Exception {
        //Arrange
        Citizen citizen1 = Citizen.builder()
                .lastName("Hamza")
                .socialInsurance("HAMRE72112213").build();
        //Act
        boolean successful = permitService.generateQR(citizen1);
        //Assert
        assertTrue(successful);
    }
}
