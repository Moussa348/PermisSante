package com.keita.permis;

import com.keita.permis.model.Administrator;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.User;
import com.keita.permis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class DatabaseRunner implements CommandLineRunner {

    @Autowired
    UserService userService;

    private void loadUsers() throws ParseException {
        List<User> users = Arrays.asList(
                new Citizen(
                        "Massou",
                        "massou",
                        "M",
                        "massou@gmail.com",
                        "massou123",
                        "5143435478",
                        "mtl",
                        new SimpleDateFormat("yyyy-MM-dd").parse("1998-11-23"),
                        "MASSOUMA980725",
                        false,
                        false,
                        null
                ),
                new Citizen(
                        "Cancre",
                        "Cancre",
                        "M",
                        "cancre@gmail.com",
                        "cancre123",
                        "5143435478",
                        "mtl",
                        new SimpleDateFormat("yyyy-MM-dd").parse("1996-10-22"),
                        "CANCC961022",
                        false,
                        false,
                        null
                ),
                new Administrator(),
                new Administrator()
        );

        userService.registerListUser(users);
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
    }
}
