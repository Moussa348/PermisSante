package com.keita.permis.controller;

import com.keita.permis.service.PermitService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/permit")
@CrossOrigin(origins = "http://localhost:4600")
@Log
public class PermitController {

    @Autowired
    private PermitService permitService;

    @PostMapping("/generate")
    public boolean generatePermit(@RequestBody String email) {
        try {
            return permitService.generatePermit(email);
        } catch (Exception e) {
            log.warning(e.getMessage().toUpperCase());
            return false;
        }
    }

}
