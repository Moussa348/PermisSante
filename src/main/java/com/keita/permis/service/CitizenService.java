package com.keita.permis.service;

import com.keita.permis.dto.UserSubmitForm;
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

    public boolean registration(UserSubmitForm form) {
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

    private boolean ifMinorCheckIfParentExist(UserSubmitForm form, LocalDate dateOfBirth) {
        int ageMin = 18;
        if (getAgeFromLocalDate(dateOfBirth) < ageMin)
            return citizenRepository
                    .existsByEmailAndFirstNameAndLastName(
                            form.getEmailParent(), form.getFirstNameParent(), form.getLastNameParent());
        return true;
    }

    private int getAgeFromLocalDate(LocalDate dateOfBirth){
        return Period.between(dateOfBirth,LocalDate.now()).getYears();
    }

    private boolean saveCitizen(UserSubmitForm form, LocalDate dateOfBirth) {
        if (form.getPassword().equals(form.getPasswordAgain())) {
            Citizen citizen =
                    Citizen.builder()
                            .firstName(form.getFirstName()).lastName(form.getLastName())
                            .email(form.getEmail()).password(form.getPassword())
                            .cellNumber(form.getCellNumber()).city(form.getCity())
                            .dateOfBirth(dateOfBirth)
                            .socialInsurance(form.getSocialInsurance()).build();
            citizenRepository.save(citizen);

            //TODO: Move into PermitService
            /*


            PermitCategory permitCategory = PermitCategory.ADULT.determinePermitCategory(getAgeFromLocalDate(dateOfBirth));
            PermitType permitType = citizen.isVaccinated()? PermitType.VACCINE: PermitType.TEST;
            Permit permit =
                    Permit.builder()
                            .restrictedAreas("").permitCategory(permitCategory)
                            .citizen(citizen).permitType(permitType).build();
            permitRepository.save(permit);
             */
            return true;
        }
        return false;
    }


}
