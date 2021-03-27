package com.keita.permis.service;

import com.keita.permis.dto.SubmitForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

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

    public boolean registration(SubmitForm form) {
        if (!citizenRepository.existsByEmail(form.getEmail())) {

            LocalDate dateOfBirth = dateFormatter(form.getDateOfBirth());

            if (dateOfBirth != null) {
                if (ifMinorCheckIfParentExist(form, dateOfBirth)) {

                    return saveCitizen(form, dateOfBirth);
                }
            }
        }
        return false;
    }

    private LocalDate dateFormatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean ifMinorCheckIfParentExist(SubmitForm form, LocalDate dateOfBirth) {
        if (getAgeFromLocalDate(dateOfBirth) < Integer.parseInt(Objects.requireNonNull(environment.getProperty("age.min"))))
            return citizenRepository
                    .existsByEmailAndFirstNameAndLastName(
                            form.getEmailParent(), form.getFirstNameParent(), form.getLastNameParent());
        return true;
    }



    private int getAgeFromLocalDate(LocalDate dateOfBirth){
        return Period.between(dateOfBirth,LocalDate.now()).getYears();
    }

    private boolean saveCitizen(SubmitForm form, LocalDate dateOfBirth) {
        if (form.getPassword().equals(form.getPasswordAgain())) {
            Citizen citizen =
                    Citizen.builder()
                            .firstName(form.getFirstName()).lastName(form.getLastName())
                            .email(form.getEmail()).password(form.getPassword())
                            .cellNumber(form.getCellNumber()).city(form.getCity())
                            .dateOfBirth(dateOfBirth)
                            .socialInsurance(form.getSocialInsurance()).build();

            if(getAgeFromLocalDate(dateOfBirth) < Integer.parseInt(Objects.requireNonNull(environment.getProperty("age.min"))))
                citizen.setParent(citizenRepository
                        .findByEmailAndFirstNameAndLastName(
                                form.getEmailParent(),
                                form.getFirstNameParent(),
                                form.getLastNameParent()).get());
            citizenRepository.save(citizen);
            return true;
        }
        return false;
    }
}
