package com.keita.permis.service;

import com.keita.permis.dto.LoginForm;
import com.keita.permis.model.Citizen;
import com.keita.permis.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Test
    public void login() {
        //Arrange
        User user =  Citizen.builder()
                .email("massou@gmail.com")
                .password("massou123").build();
        LoginForm loginForm = new LoginForm("massou@gmail.com","massou123");
        //Act

        //Assert
        assertTrue(loginService.login(loginForm));
    }
}
