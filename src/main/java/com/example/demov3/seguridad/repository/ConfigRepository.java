package com.example.demov3.seguridad.repository;

import com.example.demov3.seguridad.domain.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConfigRepository extends JpaRepository<Config, Long> {

    @Query("SELECT c FROM Config c WHERE TRIM(c.usuario) = :usuario ORDER BY c.id")
    List<Config> findByUsuarioTrim(@Param("usuario") String usuario);
}
