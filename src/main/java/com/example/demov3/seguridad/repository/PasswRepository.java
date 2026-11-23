package com.example.demov3.seguridad.repository;

import com.example.demov3.seguridad.domain.Passw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswRepository extends JpaRepository<Passw, Long> {

    // trim: los campos C(n) de dBase vienen rellenos con espacios; el login legacy compara con ALLTRIM
    @Query("SELECT p FROM Passw p WHERE TRIM(p.usuario) = :usuario")
    Optional<Passw> findByUsuarioTrim(@Param("usuario") String usuario);
}
