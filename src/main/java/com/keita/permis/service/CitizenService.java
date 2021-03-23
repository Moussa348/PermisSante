package com.keita.permis.service;

import com.keita.permis.dto.SubmitForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    private final int AGE_MIN = 18;

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
        if (getAgeFromLocalDate(dateOfBirth) < AGE_MIN)
            return citizenRepository
                    .existsByEmailAndFirstNameAndLastName(
                            form.getEmailParent(), form.getFirstNameParent(), form.getLastNameParent());
        return true;
    }

    //TODO : move into a utils method
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

            if(getAgeFromLocalDate(dateOfBirth) < AGE_MIN)
                citizen.setParent(citizenRepository
                        .findByFirstNameAndLastNameAndEmail(
                                form.getFirstNameParent(),
                                form.getLastNameParent(),
                                form.getEmailParent()).get());
            citizenRepository.save(citizen);
            return true;
        }
        return false;
    }


}
