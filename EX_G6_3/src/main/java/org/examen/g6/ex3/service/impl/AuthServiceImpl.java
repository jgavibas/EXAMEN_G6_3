package org.examen.g6.ex3.service.impl;

import lombok.RequiredArgsConstructor;
import org.examen.g6.ex3.repository.RolRepository;
import org.examen.g6.ex3.repository.UsuarioRepository;
import org.examen.g6.ex3.service.AuthService;
import org.examen.g6.ex3.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    public String login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email,password));
        var user = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("ERROR USUARIO NO ENCONTRADO"));
        var token = jwtService.generateToken(user);

        return token;
    }

}
