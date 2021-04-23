package com.keita.permis.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PoolServiceTest {

    @Mock
    PermitService permitService;

    @InjectMocks
    PoolService poolService;
}
