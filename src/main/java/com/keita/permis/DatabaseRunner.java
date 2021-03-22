package com.keita.permis;

import com.keita.permis.dto.SubmitForm;
import com.keita.permis.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@Order(1)
public class DatabaseRunner implements CommandLineRunner {

    @Autowired
    private CitizenService citizenService;

    private void loadUsers() throws ParseException {
        SubmitForm form1 =
                SubmitForm.builder()
                        .firstName("Karim").lastName("Mihoubi")
                        .gender("M").email("kMihoubi@gmail.com").password("karim123")
                        .passwordAgain("karim123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIHOUKa1234390").dateOfBirth("1976-02-01").build();

        citizenService.registration(form1);

        SubmitForm form2 =
                SubmitForm.builder()
                        .firstName("Mika").lastName("Kami")
                        .gender("F").email("mikaKami@gmail.com").password("mika123")
                        .passwordAgain("mika123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIKA45678765").dateOfBirth("2004-12-23")
                        .firstNameParent("Karim").lastNameParent("Mihoubi").emailParent("kMihoubi@gmail.com").build();
        citizenService.registration(form2);
    }

    @Override
    public void run(String... args) throws Exception {
       loadUsers();
    }
}
