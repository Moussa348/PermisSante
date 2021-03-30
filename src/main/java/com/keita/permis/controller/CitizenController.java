package com.keita.permis.controller;

import com.keita.permis.dto.SubmitForm;
import com.keita.permis.service.CitizenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/citizen")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Logger logger = LoggerFactory.getLogger(CitizenController.class);

    @PostMapping("/registration")
    public boolean registration(@Valid @RequestBody SubmitForm submitForm){
        ResponseEntity<String> responseEntity =
                restTemplate
                        .getForEntity("http://localhost:9093/ministry/search" + "/"+ submitForm.getSocialInsurance(),String.class);
        if (Objects.equals(responseEntity.getBody(), "TEST") || Objects.equals(responseEntity.getBody(), "VACCINE")){
            submitForm.setTypePermit(responseEntity.getBody());
            logger.info(responseEntity.getBody());
            return citizenService.registration(submitForm);
        }
        else
            return false;
    }
}
