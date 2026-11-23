package com.example.demov3.seguridad.service;

import com.example.demov3.seguridad.domain.Passw;
import com.example.demov3.seguridad.repository.PasswRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LegacyUserDetailsService implements UserDetailsService {

    public static final String GRUPO_SUPER = "VSIAF";
    public static final String GRUPO_ADMIN = "Admin";
    public static final String GRUPO_USER = "User";

    private final PasswRepository passwRepository;

    public LegacyUserDetailsService(PasswRepository passwRepository) {
        this.passwRepository = passwRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Passw passw = passwRepository.findByUsuarioTrim(username.trim())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.withUsername(passw.getUsuario().trim())
                .password(passw.getPsw() == null ? "" : passw.getPsw())
                .roles(rolDe(passw.getGrupo()))
                // PASSW.TIPO = .T. usuario habilitado (segurida.scx lo muestra como estado)
                .disabled(!Boolean.TRUE.equals(passw.getTipo()))
                .build();
    }

    // Grupo legacy -> rol: VSIAF = SUPER, Admin = ADMIN, User = USER. Desconocido = sin privilegios (USER)
    static String rolDe(String grupo) {
        String g = grupo == null ? "" : grupo.trim();
        if (GRUPO_SUPER.equals(g)) {
            return "SUPER";
        }
        if (GRUPO_ADMIN.equals(g)) {
            return "ADMIN";
        }
        return "USER";
    }
}
