package org.examen.g6.ex3.service.impl;

import lombok.RequiredArgsConstructor;
import org.examen.g6.ex3.aggregates.constants.Constants;
import org.examen.g6.ex3.aggregates.response.ReniecResponse;
import org.examen.g6.ex3.aggregates.response.UsuarioResponse;
import org.examen.g6.ex3.client.ReniecClient;
import org.examen.g6.ex3.entities.Rol;
import org.examen.g6.ex3.entities.Role;
import org.examen.g6.ex3.entities.Usuario;
import org.examen.g6.ex3.redis.RedisService;
import org.examen.g6.ex3.repository.RolRepository;
import org.examen.g6.ex3.repository.UsuarioRepository;
import org.examen.g6.ex3.retrofit.ReniecService;
import org.examen.g6.ex3.retrofit.impl.ReniecClientImpl;
import org.examen.g6.ex3.util.Util;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.examen.g6.ex3.service.UsuarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ReniecClient reniecClient;
    // private final RestTemplate restTemplate;
    private final RedisService redisService;
    ReniecService reniecServiceRetrofit = ReniecClientImpl.getClient().create(ReniecService.class);

    @Value("${token.api}")
    private String tokenapi;

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
    public Optional<Usuario> register(Usuario usuario) {
        usuario.setRoles(Collections.singleton(getRoles(Role.USER)));
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        return Optional.of(usuarioRepository.save(usuario));
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
    public Optional<Usuario> update(Long id, Usuario usuario) {
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

            return Optional.of(usuarioRepository.save(usuarioTmp));
        }
        else {
            throw new UsernameNotFoundException("USUARIO NO ENCONTRADO");
        }
    }

    @Override
    public Optional<Usuario> delete(Long id) {
        Optional<Usuario> usuarioEncontrado = Optional.ofNullable(usuarioRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("USUARIO NO ENCONTRADO")));;

        if(usuarioEncontrado.isPresent()){
            Usuario usuarioTmp = usuarioEncontrado.get();

            usuarioRepository.delete(usuarioTmp);

            return Optional.of(usuarioTmp);
        }
        else {
            throw new UsernameNotFoundException("USUARIO NO ENCONTRADO");
        }
    }

    @Override
    public UsuarioResponse buscarDatosReniec(String dni) {
        try {
            ReniecResponse reniecResponse = executionReniec(dni);

            executionReniec(reniecResponse, 300);
            return new UsuarioResponse(Constants.OK_DNI_CODE,Constants.OK_DNI_MESS,Optional.of(reniecResponse));
        }catch (Exception e){
            return new UsuarioResponse(Constants.ERROR_DNI_CODE,Constants.ERROR_DNI_MESS,Optional.empty());
        }
    }

    private ReniecResponse executionReniec(String documento){

        String redisInfo = redisService.getDataDesdeRedis(Constants.REDIS_KEY_API_PERSON+documento);
        if (Objects.nonNull(redisInfo)){
            return Util.convertirDesdeString(redisInfo,ReniecResponse.class);
        } else {
            String auth = "Bearer "+tokenapi;
            ReniecResponse response = reniecClient.getPersonaReniec(documento,auth);
            String dataForRedis = Util.convertirAString(response);
            redisService.guardarEnRedis(Constants.REDIS_KEY_API_PERSON+documento,dataForRedis,Constants.REDIS_EXP);
            return response;
        }
    }

    private void executionReniec(ReniecResponse datos, int tiempo){
        redisService.guardarEnRedis(Constants.REDIS_KEY_API_PERSON + datos.getNumeroDocumento(),
                datos.toString(), tiempo);
    }

    private Rol getRoles(Role rolBuscado){
        return rolRepository.findByNombreRol(rolBuscado.name())
                .orElseThrow(() -> new RuntimeException("EL ROL BUSCADO NO EXISTE : "
                        + rolBuscado.name()));
    }
}
