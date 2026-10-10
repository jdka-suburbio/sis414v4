package com.example.demov3.Entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "auxiliar", uniqueConstraints = {@UniqueConstraint(columnNames = {"ENTIDAD", "UNIDAD", "CODCONT", "CODAUX"})})
public class Auxiliar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ENTIDAD")
    private String entidad;

    @Column(name = "UNIDAD")
    private String unidad;

    @Column(name = "CODCONT")
    private Long codcont;

    @Column(name = "CODAUX")
    private Long codaux;

    @Column(name = "NOMAUX")
    private String nomaux;

    @Column(name = "OBSERV")
    private String observ;

    @Column(name = "FEULT")
    private Date feult;

    @Column(name = "USUAR")
    private String usuar;

    public Auxiliar() {
    }

    public Auxiliar(Long id,String entidad, String unidad, Long codaux, Long codcont, String nomaux, String observ, Date feult, String usuar) {
        this.id = id;
        this.entidad = entidad;
        this.unidad = unidad;
        this.codaux = codaux;
        this.codcont = codcont;
        this.nomaux = nomaux;
        this.observ = observ;
        this.feult = feult;
        this.usuar = usuar;
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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Long getCodcont() {
        return codcont;
    }

    public void setCodcont(Long codcont) {
        this.codcont = codcont;
    }

    public Long getCodaux() {
        return codaux;
    }

    public void setCodaux(Long codaux) {
        this.codaux = codaux;
    }

    public String getNomaux() {
        return nomaux;
    }

    public void setNomaux(String nomaux) {
        this.nomaux = nomaux;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
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
