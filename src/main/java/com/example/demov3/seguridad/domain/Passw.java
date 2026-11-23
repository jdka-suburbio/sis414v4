package com.example.demov3.seguridad.domain;

import jakarta.persistence.*;

import java.util.Date;

// Tabla dBase PASSW (usuarios del sistema VSIAF)
@Entity
@Table(name = "passw", uniqueConstraints = {@UniqueConstraint(columnNames = {"USUARIO"})})
public class Passw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USUARIO", length = 10)
    private String usuario;

    @Column(name = "COMPLETO", length = 40)
    private String completo;

    @Column(name = "DES", length = 50)
    private String des;

    // Clave cifrada con el algoritmo legacy (encripta de passw.spr); no es un hash
    @Column(name = "PSW", length = 10)
    private String psw;

    // Campo logico (L) en dBase: usuario habilitado
    @Column(name = "TIPO")
    private Boolean tipo;

    // User = operario, Admin = administrador, VSIAF = superusuario
    @Column(name = "GRUPO", length = 8)
    private String grupo;

    @Column(name = "FEULT")
    private Date feult;

    @Column(name = "USUAR", length = 8)
    private String usuar;

    public Passw() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCompleto() {
        return completo;
    }

    public void setCompleto(String completo) {
        this.completo = completo;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
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
