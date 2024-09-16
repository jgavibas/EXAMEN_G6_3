package org.examen.g6.ex3.service;

import org.examen.g6.ex3.entities.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService {
    UserDetailsService userDetailsService();

    Usuario register(Usuario usuario);

    String login(String email, String password);

    Usuario getByDni(String dni);

    List<Usuario> getAll();

    Usuario update(Long id, Usuario usuario);

    Usuario delete(Long id);
}
