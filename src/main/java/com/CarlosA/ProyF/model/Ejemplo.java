package com.CarlosA.ProyF.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "game")
public class Ejemplo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name="anio", nullable = false)
    private Integer anio;
    @Column(name="imagen", nullable = false, length = 100)
    private String imagen;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="foto", columnDefinition="longblob", nullable=true)
    private byte[] foto;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;

    @Column(name="created_at")
    private LocalDateTime created_at = LocalDateTime.now();
    @Column(name="updated_at")
    private LocalDateTime updated_at = LocalDateTime.now();

    public Ejemplo() {
    }
    public Ejemplo(String nombre, Integer anio, Company company) {
    this.nombre = nombre;
    this.anio = anio;
    this.company = company;
    }


    
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public byte[] getFoto() {
        return foto;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    public LocalDateTime getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getAnio() {
        return anio;    
    }
    public void setAnio(Integer anio) {
        this.anio = anio;
    }
    public LocalDateTime getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
    return "Ejemplo{" +
    "id=" + id +
    ", nombre='" + nombre + '\'' +
    ", anio=" + anio +
    '}';
    }
    

}

