package com.example.demov3.catalogo.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "codcont", uniqueConstraints = {@UniqueConstraint(columnNames = {"CODCONT"})})
public class Codcont {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODCONT")
    private Long codcont;

    @Column(name = "NOMBRE", length = 50)
    private String nombre;

    @Column(name = "VIDAUTIL")
    private Integer vidautil;

    // Campo memo (FPT) en dBase
    @Column(name = "OBSERV", columnDefinition = "TEXT")
    private String observ;

    @Column(name = "DEPRECIAR")
    private Boolean depreciar;

    @Column(name = "ACTUALIZAR")
    private Boolean actualizar;

    @Column(name = "FEULT")
    private Date feult;

    @Column(name = "USUAR", length = 8)
    private String usuar;

    public Codcont() {
    }

    public Codcont(Long id, Long codcont, String nombre, Integer vidautil, String observ, Boolean depreciar, Boolean actualizar, Date feult, String usuar) {
        this.id = id;
        this.codcont = codcont;
        this.nombre = nombre;
        this.vidautil = vidautil;
        this.observ = observ;
        this.depreciar = depreciar;
        this.actualizar = actualizar;
        this.feult = feult;
        this.usuar = usuar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodcont() {
        return codcont;
    }

    public void setCodcont(Long codcont) {
        this.codcont = codcont;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getVidautil() {
        return vidautil;
    }

    public void setVidautil(Integer vidautil) {
        this.vidautil = vidautil;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }

    public Boolean getDepreciar() {
        return depreciar;
    }

    public void setDepreciar(Boolean depreciar) {
        this.depreciar = depreciar;
    }

    public Boolean getActualizar() {
        return actualizar;
    }

    public void setActualizar(Boolean actualizar) {
        this.actualizar = actualizar;
    }

    public Date getFeult() {
        return feult;
    }

    public void setFeult(Date feult) {
        this.feult = feult;
    }

    public String getUsuar() {
        return usuar;
    }

    public void setUsuar(String usuar) {
        this.usuar = usuar;
    }
}
