package com.keita.permis;

import com.keita.permis.model.Administrator;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.User;
import com.keita.permis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class DatabaseRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    private void loadUsers() throws ParseException {

        List<User> users = Arrays.asList(
                Citizen.builder()
                        .firstName("Massou")
                        .lastName("massou")
                        .gender("M")
                        .email("massou@gmail.com")
                        .password("massou123")
                        .cellNumber("5143435478")
                        .city("mtl")
                        .dateOfBirth(LocalDate.of(1996,11,2))
                        .socialInsurance("MASSOUMA980725").build()

                , Citizen.builder()
                        .firstName("Cancre")
                        .lastName("Cancre")
                        .gender("M")
                        .email("cancre@gmail.com")
                        .password("cancre123")
                        .cellNumber("5143435478")
                        .city("mtl")
                        .dateOfBirth(LocalDate.of(1996,11,2))
                        .socialInsurance("CANCC961022").build(),
                new Administrator()
        );

        userRepository.saveAll(users);
    }

    @Override
    public void run(String... args) throws Exception {
        //loadUsers();
    }
}
