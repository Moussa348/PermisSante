package com.keita.permis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.keita.permis.dto.RequestPermitForm;
import com.keita.permis.enums.PermitType;
import com.keita.permis.model.Citizen;
import com.keita.permis.service.PermitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/permit")
@CrossOrigin(origins = "http://localhost:4600")
public class PermitController {

    @Autowired
    private PermitService permitService;
    private final Logger logger = LoggerFactory.getLogger(PermitController.class);

    @PostMapping("/generate")
    public boolean generatePermit(@RequestBody String email) {
        try {
            return permitService.generatePermit(email);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    /*

    @GetMapping("test/{socialInsurance}")
    public Citizen get(@PathVariable String socialInsurance) throws JsonProcessingException {
        Citizen citizen = permitService.getBySocialInsuranceFromMinistry(socialInsurance);
        logger.info(citizen.toString());
        return citizen;
    }
     */
}
