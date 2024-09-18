package org.examen.g6.ex3.service.impl;

import org.examen.g6.ex3.service.AuthService;
import org.examen.g6.ex3.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {
    @Autowired
    private AuthService service;

    @Test
    void login() {
        String email = "usuario03@mail.com";
        String password = "@123AbC";


        String response = this.service.login(email, password);

        assertFalse(response.isEmpty());
    }
}