package com.keita.permis;

import com.keita.permis.dto.RequestPermitForm;
import com.keita.permis.dto.SubmitForm;
import com.keita.permis.service.CitizenService;
import com.keita.permis.service.PermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class DatabaseRunner implements CommandLineRunner {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private PermitService permitService;

    private void loadUsers() throws Exception {
        SubmitForm form1 =
                SubmitForm.builder()
                        .firstName("Karim").lastName("Mihoubi")
                        .gender("M").email("karimMihoubi@gmail.com").password("karim123")
                        .passwordAgain("karim123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIHOUKa1234390").dateOfBirth("1976-02-01").build();


        SubmitForm form2 =
                SubmitForm.builder()
                        .firstName("Mika").lastName("Mihoubi")
                        .gender("F").email("mikaMihoubi@gmail.com").password("mika123")
                        .passwordAgain("mika123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIKA45678765").dateOfBirth("2004-12-23")
                        .firstNameParent("Karim").lastNameParent("Mihoubi").emailParent("karimMihoubi@gmail.com").build();

        SubmitForm form3 =
                SubmitForm.builder()
                        .firstName("Reda").lastName("Hamza")
                        .gender("M").email("redaHamza@gmail.com").password("mika123")
                        .passwordAgain("mika123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("MIKA45678765").dateOfBirth("1967-12-23")
                        .firstNameParent("Karim").lastNameParent("Mihoubi").emailParent("karimMihoubi@gmail.com").build();
        citizenService.registration(form1);
        citizenService.registration(form2);
        citizenService.registration(form3);

    }

    private void loadPermit() throws Exception {
        RequestPermitForm requestPermitForm = RequestPermitForm.builder().email("mikaMihoubi@gmail.com")
                .password("mika123").build();
        RequestPermitForm requestPermitForm2 = RequestPermitForm.builder().email("karimMihoubi@gmail.com")
                .password("karim123").build();
        permitService.generatePermit(requestPermitForm);
        permitService.generatePermit(requestPermitForm2);
    }

    @Override
    public void run(String... args) throws Exception {
       loadUsers();
       loadPermit();
    }
}
