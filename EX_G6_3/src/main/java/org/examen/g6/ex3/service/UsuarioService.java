package org.examen.g6.ex3.service;

import org.examen.g6.ex3.entities.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    UserDetailsService userDetailsService();

    Usuario register(Usuario usuario);

    Optional<Usuario> getByDni(String dni);

    List<Usuario> getAll();

    Usuario update(Long id, Usuario usuario);

    Usuario delete(Long id);
}
