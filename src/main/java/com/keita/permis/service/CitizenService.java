package com.keita.permis.service;

import com.keita.permis.dto.UserSubmitForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.repository.CitizenRepository;
import com.keita.permis.repository.PermitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private PermitRepository permitRepository;

    public boolean registration(@NotEmpty UserSubmitForm form) {
        if (!citizenRepository.existsByEmail(form.getEmail())) {

            LocalDate dateOfBirth = dateFormatter(form.getDateOfBirth());

            if (dateOfBirth != null) {
                Citizen citizen =
                        Citizen.builder()
                                .firstName(form.getFirstName()).lastName(form.getLastName())
                                .email(form.getEmail()).password(form.getPassword())
                                .cellNumber(form.getCellNumber()).city(form.getCity())
                                .dateOfBirth(dateOfBirth)
                                .socialInsurance(form.getSocialInsurance()).build();
                return true;
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

    private boolean ifMinorCheckIfParentExist(UserSubmitForm form) {
        return false;
    }


}
