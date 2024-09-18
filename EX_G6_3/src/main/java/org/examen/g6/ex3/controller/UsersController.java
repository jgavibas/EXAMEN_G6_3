package org.examen.g6.ex3.controller;

import lombok.RequiredArgsConstructor;
import org.examen.g6.ex3.aggregates.constants.Constants;
import org.examen.g6.ex3.aggregates.request.LogInRequest;
import org.examen.g6.ex3.aggregates.request.UsuarioRequest;
import org.examen.g6.ex3.aggregates.response.LogInResponse;
import org.examen.g6.ex3.aggregates.response.ReniecResponse;
import org.examen.g6.ex3.aggregates.response.UsuarioResponse;
import org.examen.g6.ex3.entities.Usuario;
import org.examen.g6.ex3.service.AuthService;
import org.examen.g6.ex3.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(
        name = "API GESTION DE USUARIOS",
        description = "Esta api contiene los endPoints para la gestión de USUARIOS, " +
                "desde crear hasta eliminar de manera logica")
public class UsersController {
    private final UsuarioService usuarioService;
    private final AuthService authService;

    @GetMapping("/healthcheck")
    public ResponseEntity<Boolean> healthcheck() {
        return ResponseEntity.ok(true);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody UsuarioRequest usuarioRequest){

        UsuarioResponse response = usuarioService.buscarDatosReniec(usuarioRequest.getNumDoc());

        if(response.getCode().equals(Constants.OK_DNI_CODE)){
            Optional<ReniecResponse> data = response.getData();


            /*
            Usuario usuario = new Usuario(usuarioRequest.getNombres(),
                    usuarioRequest.getApellidos(),
                    usuarioRequest.getEmail(),
                    usuarioRequest.getPassword(),
                    usuarioRequest.getTipoDoc(),
                    usuarioRequest.getNumDoc(),
                        "");
             */

            Usuario usuario = new Usuario(data.get().getNombres(),
                    String.format("%s %s", data.get().getApellidoPaterno(), data.get().getApellidoMaterno()).trim(),
                    usuarioRequest.getEmail(),
                    usuarioRequest.getPassword(),
                    data.get().getTipoDocumento(),
                    usuarioRequest.getNumDoc(),
                    data.get().getDigitoVerificador());

            //UsuarioResponse response = new UsuarioResponse();
            response.setData(usuarioService.register(usuario));

            if(response.getData().isPresent()){
                response.setCode(Constants.OK_SAVE);
                response.setMessage(Constants.OK_SAVE_MESS);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }else{
                response.setCode(Constants.ERROR_SAVE);
                response.setMessage(Constants.ERROR_SAVE_MESS);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> login(@RequestBody LogInRequest logInRequest){
        LogInResponse response = new LogInResponse();

        response.setToken(authService.login(logInRequest.getEmail(), logInRequest.getPassword()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<UsuarioResponse> getByDni(@PathVariable("dni") String dni) {
        Optional<Usuario> usuario = usuarioService.getByDni(dni);

        UsuarioResponse response = new UsuarioResponse();
        response.setData(usuario);

        if(response.getData().isPresent()){
            response.setCode(Constants.OK_CODE_LIST);
            response.setMessage(Constants.OK_MESS_LIST);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setCode(Constants.ERROR_CODE_LIST_EMPTY);
            response.setMessage(Constants.ERROR_MESS_LIST_EMPTY);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<UsuarioResponse> getAll() {
        UsuarioResponse response = new UsuarioResponse();
        response.setData(Optional.of(usuarioService.getAll()));

        if(response.getData().isPresent()){
            response.setCode(Constants.OK_CODE_LIST);
            response.setMessage(Constants.OK_MESS_LIST);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setCode(Constants.ERROR_CODE_LIST_EMPTY);
            response.setMessage(Constants.ERROR_MESS_LIST_EMPTY);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(@PathVariable("id") Long id, @RequestBody UsuarioRequest usuarioRequest){
        Usuario usuario = new Usuario(usuarioRequest.getNombres(), usuarioRequest.getApellidos(),
                usuarioRequest.getEmail(), usuarioRequest.getPassword(),
                usuarioRequest.getTipoDoc(), usuarioRequest.getNumDoc(),
                "");
        usuario.setId(id);

        UsuarioResponse response = new UsuarioResponse();
        response.setData(usuarioService.update(id, usuario));

        if(response.getData().isPresent()){
            response.setCode(Constants.OK_SAVE);
            response.setMessage(Constants.OK_SAVE_MESS);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setCode(Constants.ERROR_SAVE);
            response.setMessage(Constants.ERROR_SAVE_MESS);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponse> delete(@PathVariable("id") Long id){
        UsuarioResponse response = new UsuarioResponse();
        response.setData(usuarioService.delete(id));

        if(response.getData().isPresent()){
            response.setCode(Constants.OK_SAVE);
            response.setMessage(Constants.OK_SAVE_MESS);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.setCode(Constants.ERROR_SAVE);
            response.setMessage(Constants.ERROR_SAVE_MESS);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }


}
