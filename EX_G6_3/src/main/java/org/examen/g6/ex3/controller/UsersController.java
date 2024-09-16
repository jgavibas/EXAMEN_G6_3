package org.examen.g6.ex3.controller;

import lombok.RequiredArgsConstructor;
import org.examen.g6.ex3.aggregates.request.LogInRequest;
import org.examen.g6.ex3.aggregates.request.UsuarioRequest;
import org.examen.g6.ex3.aggregates.response.LogInResponse;
import org.examen.g6.ex3.entities.Usuario;
import org.examen.g6.ex3.repository.UsuarioRepository;
import org.examen.g6.ex3.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(
        name = "API GESTION DE USUARIOS",
        description = "Esta api contiene los endPoints para la gestión de USUARIOS, " +
                "desde crear hasta eliminar de manera logica")
public class UsersController {
    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody UsuarioRequest usuarioRequest){
        Usuario usuario = new Usuario();
        return ResponseEntity.ok(usuarioService.register(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> login(@RequestBody LogInRequest logInRequest){
        LogInResponse response = new LogInResponse();

        response.setToken(usuarioService.login(logInRequest.getEmail(), logInRequest.getPassword()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Usuario> getByDni(@RequestParam String dni) {
        return ResponseEntity.ok(usuarioService.getByDni(dni));
    }

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@RequestParam Long id, @RequestBody UsuarioRequest usuarioRequest){
        Usuario usuario = new Usuario();
        return ResponseEntity.ok(usuarioService.update(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> delete(@RequestParam Long id){
        return ResponseEntity.ok(usuarioService.delete(id));
    }


}
