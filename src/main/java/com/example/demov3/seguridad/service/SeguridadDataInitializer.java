package com.example.demov3.seguridad.service;

import com.example.demov3.seguridad.domain.Config;
import com.example.demov3.seguridad.domain.Passw;
import com.example.demov3.seguridad.repository.ConfigRepository;
import com.example.demov3.seguridad.repository.PasswRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Datos iniciales de seguridad, tomados de PASSW.DBF y CONFIG.DBF del sistema VSIAF.
 * Solo siembra una tabla si esta vacia: es seguro ejecutarlo en cada arranque.
 * PSW va con el cifrado legacy ("WW[[" = clave 1234); cambiar la clave de admin tras el primer ingreso.
 */
@Component
public class SeguridadDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SeguridadDataInitializer.class);

    private final PasswRepository passwRepository;
    private final ConfigRepository configRepository;

    public SeguridadDataInitializer(PasswRepository passwRepository, ConfigRepository configRepository) {
        this.passwRepository = passwRepository;
        this.configRepository = configRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (passwRepository.count() == 0) {
            Passw admin = new Passw();
            admin.setUsuario("admin");
            admin.setCompleto("administrador");
            admin.setDes("");
            admin.setPsw("WW[[");
            admin.setTipo(true);
            admin.setGrupo("Admin");
            admin.setFeult(Date.from(LocalDate.of(2011, 8, 22).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            admin.setUsuar("admin");
            passwRepository.save(admin);
            log.info("Usuario inicial 'admin' creado");
        }
        if (configRepository.count() == 0) {
            Config config = new Config();
            config.setEntidad("143");
            config.setDescent("Universidad Autónoma Tomás Frías");
            config.setUnidad("AFJ");
            config.setDescuni("UNIDAD DE ACTIVOS FIJOS");
            config.setUsuario("admin");
            configRepository.save(config);
            log.info("Configuracion inicial de 'admin' creada");
        }
    }
}
