package org.examen.g6.ex3.service.impl;

import org.examen.g6.ex3.aggregates.response.UsuarioResponse;
import org.examen.g6.ex3.client.ReniecClient;
import org.examen.g6.ex3.entities.Usuario;
import org.examen.g6.ex3.redis.RedisService;
import org.examen.g6.ex3.repository.RolRepository;
import org.examen.g6.ex3.repository.UsuarioRepository;
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

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private ReniecClient reniecClient;
    @Mock
    private RedisService redisService;

    @InjectMocks
    private UsuarioServiceImpl service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new UsuarioServiceImpl(usuarioRepository, rolRepository, reniecClient, redisService);
    }


    @Test
    void update() {
        Long id = (long) 20;
        Usuario usuario = new Usuario(id, "xxxx", "xxxx", "70840118x@mail.com", "70840118x", "70840118", "1");

        Optional<Usuario> response = service.update(id, usuario);

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

        assertEquals(Constants.OK_DNI_CODE, response.getCode());
    }
}