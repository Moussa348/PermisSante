package com.keita.permis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keita.permis.model.Citizen;
import com.keita.permis.repository.CitizenRepository;
import com.keita.permis.repository.PermitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class PoolService {

    @Autowired
    private PermitRepository permitRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private Environment environment;

    public Integer disableAllPermitExpired(){
        return permitRepository.disablePermit(LocalDate.now());
    }

    public Integer renewPermitExpiredDaily(){
        //GetListCitizen

        //foreach -> call ministry

        /*
            -if nothing do not renew
            -if something --> set Vaccinated from citizen received from ministry
                ---> pass citizen to permitService.renewPermit(Citizen citizen)
                -->done
         */
        return null;
    }

    public Citizen getBySocialInsuranceFromMinistry(String socialInsurance) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate
                        .getForEntity(environment.getProperty("api.url.renewal") +
                                socialInsurance, String.class);
        if (!Objects.requireNonNull(responseEntity.getBody()).isEmpty())
            return mapper.readValue(responseEntity.getBody(), Citizen.class);
        return null;
    }

}
