package org.examen.g6.ex3.service.impl;

import lombok.RequiredArgsConstructor;
import org.examen.g6.ex3.entities.Usuario;
import org.examen.g6.ex3.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.examen.g6.ex3.service.UsuarioService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username)
                    throws UsernameNotFoundException {
                return usuarioRepository.findByEmail(username).orElseThrow(
                        ()-> new UsernameNotFoundException("USUARIO NO ENCONTRADO"));
            }
        };
    }

    @Override
    public Usuario register(Usuario usuario) {
        return null;
    }

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public Usuario getByDni(String dni) {
        return null;
    }

    @Override
    public List<Usuario> getAll() {
        return List.of();
    }

    @Override
    public Usuario update(Long id, Usuario usuario) {
        return null;
    }

    @Override
    public Usuario delete(Long id) {
        return null;
    }
}
