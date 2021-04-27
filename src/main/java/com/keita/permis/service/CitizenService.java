package com.keita.permis.service;

import com.keita.permis.dto.AuthForm;
import com.keita.permis.enums.Role;
import com.keita.permis.model.Citizen;
import com.keita.permis.repository.CitizenRepository;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Optional;

@Service
@Log
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private Environment environment;

    public String authentication(AuthForm authForm) {
        Optional<Citizen> citizenOptional = citizenRepository.findByEmailAndPassword(authForm.getEmail(), authForm.getPassword());
        if (citizenOptional.isPresent() && citizenOptional.get().isActive())
            return citizenOptional.get().getEmail();
        return "";
    }

    public Integer registration(Citizen citizen) {
        if (!citizenRepository.existsByEmail(citizen.getEmail())) {

            Optional<Citizen> parent = ifMinorCheckIfParentExist(citizen);

            if (parent.isEmpty() && getAgeFromLocalDate(citizen.getDateOfBirth()) <= Integer.parseInt(Objects.requireNonNull(environment.getProperty("age.min"))))
                return -1;

            parent.ifPresent(citizen::setParent);
            citizen.setRegistrationDate(LocalDate.now());
            citizen.setRole(Role.USER);
            citizenRepository.save(citizen);
            log.info("SAVING");
            return 0;
        }
        return 1;
    }

    public Citizen viewCitizenInfo(String email){
        Optional<Citizen> citizenOptional = citizenRepository.findByEmail(email);
        return citizenOptional.orElse(null);
    }

    private Optional<Citizen> ifMinorCheckIfParentExist(Citizen citizen) {
        if (getAgeFromLocalDate(citizen.getDateOfBirth()) <= Integer.parseInt(Objects.requireNonNull(environment.getProperty("age.min"))))
            return citizenRepository.findBySocialInsurance(citizen.getParent().getSocialInsurance());
        return Optional.empty();
    }

    private int getAgeFromLocalDate(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
