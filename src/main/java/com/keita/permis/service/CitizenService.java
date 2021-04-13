package com.keita.permis.service;

import com.keita.permis.controller.CitizenController;
import com.keita.permis.dto.AuthForm;
import com.keita.permis.dto.SubmitForm;
import com.keita.permis.enums.PermitType;
import com.keita.permis.model.Citizen;
import com.keita.permis.repository.CitizenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private Environment environment;

    Logger logger = LoggerFactory.getLogger(CitizenService.class);

    public String authentication(AuthForm authForm) {
        Optional<Citizen> citizenOptional = citizenRepository.findByEmailAndPassword(authForm.getEmail(), authForm.getPassword());
        if (citizenOptional.isPresent())
            return citizenOptional.get().getEmail();
        return "";
    }

    public boolean registration(Citizen citizen) {
        if (!citizenRepository.existsByEmail(citizen.getEmail())) {

            Optional<Citizen> parent = ifMinorCheckIfParentExist(citizen);

            if (parent.isEmpty() && getAgeFromLocalDate(citizen.getDateOfBirth()) <= Integer.parseInt(Objects.requireNonNull(environment.getProperty("age.min"))))
                return false;

            parent.ifPresent(citizen::setParent);
            citizenRepository.save(citizen);
            logger.info("SAVING");
            return true;
        }
        return false;
    }

    private Optional<Citizen> ifMinorCheckIfParentExist(Citizen citizen) {
        if (getAgeFromLocalDate(citizen.getDateOfBirth()) <= Integer.parseInt(Objects.requireNonNull(environment.getProperty("age.min"))))
            return citizenRepository.findByEmail(citizen.getParent().getEmail());
        return Optional.empty();
    }


    private int getAgeFromLocalDate(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
