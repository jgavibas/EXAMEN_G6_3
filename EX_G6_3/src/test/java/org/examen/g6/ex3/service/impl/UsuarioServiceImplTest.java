package org.examen.g6.ex3.service.impl;

import org.examen.g6.ex3.aggregates.response.UsuarioResponse;
import org.examen.g6.ex3.client.ReniecClient;
import org.examen.g6.ex3.entities.Usuario;
import org.examen.g6.ex3.redis.RedisService;
import org.examen.g6.ex3.repository.RolRepository;
import org.examen.g6.ex3.repository.UsuarioRepository;
import org.examen.g6.ex3.service.AuthService;
import org.examen.g6.ex3.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.examen.g6.ex3.aggregates.constants.Constants;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UsuarioServiceImplTest {

    @Autowired
    private UsuarioService service;

    @Autowired
    private AuthService serviceAuth;

    @BeforeEach
    void setUp(){

    }


    @Test
    void update() {
        Long id = (long) 21;
        Usuario usuario = new Usuario(id, "xxxx", "xxxx", "70840118x@mail.com", "70840118x", "70840118", "1");

        Optional<Usuario> response = this.service.update(id, usuario);

        assertTrue(response.isPresent());

        if(response.isPresent()) {
            assertNotEquals(0, response.get().getId(),
                    Constants.OK_SAVE_MESS);
        }
    }

    @Test
    void delete() {
        Long id = (long) 20;

        Optional<Usuario> response = service.delete(id);

        assertTrue(response.isPresent());

        if(response.isPresent()) {
            assertNotEquals(0, response.get().getId(),
                    Constants.OK_SAVE_MESS);
        }
    }

    @Test
    void register() {
        Usuario usuario = new Usuario();
        usuario.setEmail("70840118@mail.com");
        usuario.setPassword("70840118");
        usuario.setNumDoc("70840118");

        Optional<Usuario> response = service.register(usuario);

        assertTrue(response.isPresent());

        if(response.isPresent()) {
            assertNotEquals(0, response.get().getId(),
                    Constants.OK_SAVE_MESS);
        }
    }

    @Test
    void getByDni() {
        String numDoc = "70840118";

        Optional<Usuario> response = service.getByDni(numDoc);

        assertTrue(response.isPresent());

        if(response.isPresent()) {
            assertNotEquals(0, response.get().getId(),
                    Constants.OK_DNI_CODE);
        }
    }

    @Test
    void getAll() {
        List<Usuario> response = service.getAll();

        assertTrue(!response.isEmpty());
    }

    @Test
    void buscarDatosReniec() {
        String numDoc = "70840118";

        UsuarioResponse response = service.buscarDatosReniec(numDoc);

        assertTrue(response.getData().isPresent());
        assertEquals(Constants.OK_DNI_CODE, response.getCode());
    }


    @Test
    void login() {
        String email = "usuario03@mail.com";
        String password = "@123AbC";


        String response = this.serviceAuth.login(email, password);

        assertFalse(response.isEmpty());
    }

}