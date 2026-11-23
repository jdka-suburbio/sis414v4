package com.example.demov3.organizacion.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "oficina", uniqueConstraints = {@UniqueConstraint(columnNames = {"ENTIDAD", "UNIDAD", "CODOFIC"})})
public class Oficina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ENTIDAD", length = 4)
    private String entidad;

    @Column(name = "UNIDAD", length = 5)
    private String unidad;

    @Column(name = "CODOFIC")
    private Long codofic;

    @Column(name = "NOMOFIC", length = 65)
    private String nomofic;

    // Campo memo (FPT) en dBase
    @Column(name = "OBSERV", columnDefinition = "TEXT")
    private String observ;

    @Column(name = "FEULT")
    private Date feult;

    @Column(name = "USUAR", length = 8)
    private String usuar;

    @Column(name = "API_ESTADO")
    private Integer apiEstado;

    public Oficina() {
    }

    public Oficina(Long id, String entidad, String unidad, Long codofic, String nomofic, String observ, Date feult, String usuar, Integer apiEstado) {
        this.id = id;
        this.entidad = entidad;
        this.unidad = unidad;
        this.codofic = codofic;
        this.nomofic = nomofic;
        this.observ = observ;
        this.feult = feult;
        this.usuar = usuar;
        this.apiEstado = apiEstado;
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

    public Long getCodofic() {
        return codofic;
    }

    public void setCodofic(Long codofic) {
        this.codofic = codofic;
    }

    public String getNomofic() {
        return nomofic;
    }

    public void setNomofic(String nomofic) {
        this.nomofic = nomofic;
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

    public Integer getApiEstado() {
        return apiEstado;
    }

    public void setApiEstado(Integer apiEstado) {
        this.apiEstado = apiEstado;
    }
}
