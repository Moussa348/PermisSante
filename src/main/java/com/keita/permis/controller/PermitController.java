package com.keita.permis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.keita.permis.model.Citizen;
import com.keita.permis.service.PermitService;
import com.keita.permis.service.PoolService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/permit")
@CrossOrigin(origins = "http://localhost:4600")
@Log
public class PermitController {

    @Autowired
    private PermitService permitService;

    @Autowired
    private PoolService poolService;

    @PostMapping("/generate")
    public boolean generatePermit(@RequestBody String email) {
        try {
            return permitService.generatePermit(email);
        } catch (Exception e) {
            log.warning(e.getMessage());
            return false;
        }
    }


    @GetMapping("test/{socialInsurance}")
    public Citizen get(@PathVariable String socialInsurance) throws JsonProcessingException {
        Citizen citizen = poolService.getBySocialInsuranceFromMinistry(socialInsurance);
        log.info(citizen.toString());
        return citizen;
    }
}
