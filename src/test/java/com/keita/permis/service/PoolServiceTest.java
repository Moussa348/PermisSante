package com.keita.permis.service;

import com.keita.permis.model.Permit;
import com.keita.permis.repository.PermitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class PoolServiceTest {

    @Mock
    PermitService permitService;

    @Mock
    PermitRepository permitRepository;

    @InjectMocks
    PoolService poolService;

}
