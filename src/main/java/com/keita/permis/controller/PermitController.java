package com.keita.permis.controller;

import com.keita.permis.dto.RequestPermitForm;
import com.keita.permis.service.PermitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/permit")
public class PermitController {

    @Autowired
    private PermitService permitService;
    private final Logger logger = LoggerFactory.getLogger(PermitController.class);

    @PostMapping("/generate")
    public boolean generatePermit(@Valid @RequestBody RequestPermitForm requestPermitForm){
        try {
            return permitService.generatePermit(requestPermitForm);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }
}
