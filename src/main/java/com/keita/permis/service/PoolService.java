package com.keita.permis.service;

import com.keita.permis.model.Citizen;
import com.keita.permis.repository.PermitRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Objects;

@Service
@Log
public class PoolService {

    @Autowired
    private PermitRepository permitRepository;

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

    public Citizen getBySocialInsuranceFromMinistry(String socialInsurance) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Citizen> responseEntity =
                restTemplate
                        .getForEntity(environment.getProperty("api.url.renewal") +
                                socialInsurance, Citizen.class);
        if (responseEntity.hasBody()){
            log.info(Objects.requireNonNull(responseEntity.getBody()).toString());
            return responseEntity.getBody();
        }
        return null;
    }

}
