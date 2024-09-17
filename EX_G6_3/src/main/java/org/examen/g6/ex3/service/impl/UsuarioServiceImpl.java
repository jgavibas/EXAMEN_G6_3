package org.examen.g6.ex3.service.impl;

import lombok.RequiredArgsConstructor;
import org.examen.g6.ex3.entities.Rol;
import org.examen.g6.ex3.entities.Role;
import org.examen.g6.ex3.entities.Usuario;
import org.examen.g6.ex3.repository.RolRepository;
import org.examen.g6.ex3.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.examen.g6.ex3.service.UsuarioService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

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
        usuario.setRoles(Collections.singleton(getRoles(Role.USER)));
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }


    @Override
    public Optional<Usuario> getByDni(String numDoc) {
        return usuarioRepository.findByNumDoc(numDoc);
    }

    @Override
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario update(Long id, Usuario usuario) {
        Optional<Usuario> usuarioEncontrado = Optional.ofNullable(usuarioRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("USUARIO NO ENCONTRADO")));;

        if(usuarioEncontrado.isPresent()){
            Usuario usuarioTmp = usuarioEncontrado.get();

            usuarioTmp.setNombres(usuario.getNombres());
            usuarioTmp.setApellidos(usuario.getApellidos());
            usuarioTmp.setEmail(usuario.getEmail());
            usuarioTmp.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
            usuarioTmp.setTipoDoc(usuario.getTipoDoc());
            usuarioTmp.setNumDoc(usuario.getNumDoc());

            return usuarioRepository.save(usuarioTmp);
        }
        else {
            throw new UsernameNotFoundException("USUARIO NO ENCONTRADO");
        }
    }

    @Override
    public Usuario delete(Long id) {
        Optional<Usuario> usuarioEncontrado = Optional.ofNullable(usuarioRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("USUARIO NO ENCONTRADO")));;

        if(usuarioEncontrado.isPresent()){
            Usuario usuarioTmp = usuarioEncontrado.get();

            usuarioRepository.delete(usuarioTmp);

            return usuarioTmp;
        }
        else {
            throw new UsernameNotFoundException("USUARIO NO ENCONTRADO");
        }
    }

    private Rol getRoles(Role rolBuscado){
        return rolRepository.findByNombreRol(rolBuscado.name())
                .orElseThrow(() -> new RuntimeException("EL ROL BSUCADO NO EXISTE : "
                        + rolBuscado.name()));
    }
}
