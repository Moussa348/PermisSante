package com.keita.permis.controller;

import com.keita.permis.dto.RequestPermitForm;
import com.keita.permis.service.PermitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/permit")
public class PermitController {

    @Autowired
    private PermitService permitService;

    @Autowired
    private Environment environment;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Logger logger = LoggerFactory.getLogger(PermitController.class);

    @PostMapping("/generate")
    public boolean generatePermit(@Valid @RequestBody RequestPermitForm requestPermitForm) {
        try {
            return permitService.generatePermit(requestPermitForm);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    @PostMapping("/renewal")
    public boolean renewPermit(@Valid @RequestBody RequestPermitForm requestPermitForm) {
        ResponseEntity<String> responseEntity =
                restTemplate
                        .getForEntity(environment.getProperty("api.url.renewal") +
                                requestPermitForm.getCellNumber(), String.class);

        if (Objects.equals(responseEntity.getBody(), environment.getProperty("permit.type1")) ||
                Objects.equals(responseEntity.getBody(), environment.getProperty("permit.type2"))) {
            requestPermitForm.setTypePermit(responseEntity.getBody());
            try {
                return permitService.renewPermit(requestPermitForm);
            } catch (Exception e) {
                logger.warn(e.getMessage());
                return false;
            }
        }
        return false;
    }
}
