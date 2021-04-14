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

    private void loadUsers() {
        SubmitForm form1 =
                SubmitForm.builder()
                        .firstName("Reda").lastName("Hamza")
                        .gender("Male").email("developpeurspring@gmail.com").password("reda123")
                        .passwordAgain("reda123").cellNumber("5143786549").city("Montreal")
                        .socialInsurance("45456789").dateOfBirth("1976-02-01").build();
        SubmitForm form2 =
                SubmitForm.builder()
                        .firstName("Mathilde").lastName("Marie")
                        .gender("Female").email("Mathilde@gmail.com").password("mathilde123")
                        .passwordAgain("mathilde123").cellNumber("5143345678").city("Chicoutimi")
                        .socialInsurance("123456789").dateOfBirth("2005-02-01")
                        .emailParent("developpeurspring@gmail.com").firstNameParent("Reda").lastNameParent("Hamza").build();

        //citizenService.registration(form1);
        //citizenService.registration(form2);

    }


    private void loadPermit() throws Exception {
        RequestPermitForm requestPermitForm = RequestPermitForm.builder().email("developpeurspring@gmail.com")
                .password("reda123").build();
        //permitService.generatePermit(requestPermitForm);
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
        loadPermit();
    }
}
