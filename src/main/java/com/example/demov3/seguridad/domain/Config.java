package com.example.demov3.seguridad.domain;

import jakarta.persistence.*;

// Tabla dBase CONFIG: entidad y unidad con la que trabaja cada usuario
@Entity
@Table(name = "config")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ENTIDAD", length = 4)
    private String entidad;

    @Column(name = "DESCENT", length = 120)
    private String descent;

    @Column(name = "UNIDAD", length = 5)
    private String unidad;

    @Column(name = "DESCUNI", length = 120)
    private String descuni;

    @Column(name = "USUARIO", length = 10)
    private String usuario;

    public Config() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getDescent() {
        return descent;
    }

    public void setDescent(String descent) {
        this.descent = descent;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescuni() {
        return descuni;
    }

    public void setDescuni(String descuni) {
        this.descuni = descuni;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
