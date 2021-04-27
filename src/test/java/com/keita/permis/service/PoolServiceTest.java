package com.keita.permis.service;

import com.keita.permis.model.Citizen;
import com.keita.permis.repository.PermitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
public class PoolServiceTest {


    @Mock
    PermitRepository permitRepository;

    @Mock
    Environment environment;

    @InjectMocks
    PoolService poolService;


    @Test
    void getBySocialInsuranceFromMinistry() throws URISyntaxException {
        //ARRANGE
        String socialInsurance1 = "2991943866";
        when(environment.getProperty("api.url.renewal")).thenReturn("http://35.182.21.253:9093/ministry/findBySocialInsurance/");

        String socialInsurance2 = "123213124";
        when(environment.getProperty("api.url.renewal")).thenReturn("http://35.182.21.253:9093/ministry/findBySocialInsurance/");
        //ACT
        Citizen citizenExistInMinistry = poolService.getBySocialInsuranceFromMinistry(socialInsurance1);
        Citizen citizenDoNotExistInMinistry = poolService.getBySocialInsuranceFromMinistry(socialInsurance2);

        //ASSERT
        assertNotNull(citizenExistInMinistry);
        assertNull(citizenDoNotExistInMinistry);
    }


}
