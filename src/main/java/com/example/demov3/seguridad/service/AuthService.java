package com.example.demov3.seguridad.service;

import com.example.demov3.seguridad.web.dto.LoginRequest;
import com.example.demov3.seguridad.web.dto.LoginResponse;
import com.example.demov3.seguridad.domain.Config;
import com.example.demov3.seguridad.domain.Passw;
import com.example.demov3.seguridad.repository.ConfigRepository;
import com.example.demov3.seguridad.repository.PasswRepository;
import com.example.demov3.common.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    // Legacy: passw.spr cancela el programa tras 3 intentos
    private static final int MAX_INTENTOS = 3;
    private static final Duration BLOQUEO = Duration.ofMinutes(5);
    // passw.psw y passw.usuario son C(10)
    private static final int MAX_LONGITUD = 10;

    private record Intentos(int cantidad, Instant desde) {
    }

    private final Map<String, Intentos> intentos = new ConcurrentHashMap<>();

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswRepository passwRepository;
    private final ConfigRepository configRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService,
                       PasswRepository passwRepository, ConfigRepository configRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwRepository = passwRepository;
        this.configRepository = configRepository;
    }

    public LoginResponse login(LoginRequest request) {
        if (request == null || request.usuario() == null || request.usuario().isBlank()
                || request.psw() == null || request.psw().isBlank()
                || request.usuario().trim().length() > MAX_LONGITUD
                || request.psw().trim().length() > MAX_LONGITUD) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario y clave son obligatorios (maximo 10 caracteres)");
        }
        String usuario = request.usuario().trim();
        verificarBloqueo(usuario);

        Authentication auth;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, request.psw().trim()));
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario deshabilitado");
        } catch (AuthenticationException e) {
            registrarFallo(usuario);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o clave incorrectos");
        }
        intentos.remove(usuario);

        Passw passw = passwRepository.findByUsuarioTrim(usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o clave incorrectos"));
        // Legacy (main.scx): SELECT * FROM config WHERE usuario = ALLTRIM(usuariox)
        Config config = configRepository.findByUsuarioTrim(usuario).stream().findFirst().orElse(null);

        List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String grupo = passw.getGrupo() == null ? null : passw.getGrupo().trim();
        String entidad = config == null ? null : trim(config.getEntidad());
        String unidad = config == null ? null : trim(config.getUnidad());

        String token = jwtService.issue(usuario, grupo, roles, entidad, unidad);
        return new LoginResponse(token, "Bearer", jwtService.getExpirationSeconds(), usuario,
                trim(passw.getCompleto()), grupo, entidad, config == null ? null : trim(config.getDescent()),
                unidad, config == null ? null : trim(config.getDescuni()));
    }

    private void verificarBloqueo(String usuario) {
        Intentos actual = intentos.get(usuario);
        if (actual == null) {
            return;
        }
        if (Duration.between(actual.desde(), Instant.now()).compareTo(BLOQUEO) >= 0) {
            intentos.remove(usuario);
        } else if (actual.cantidad() >= MAX_INTENTOS) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Cantidad de intentos = " + MAX_INTENTOS + ", intente mas tarde");
        }
    }

    private void registrarFallo(String usuario) {
        intentos.merge(usuario, new Intentos(1, Instant.now()),
                (viejo, nuevo) -> new Intentos(viejo.cantidad() + 1, viejo.desde()));
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }
}
