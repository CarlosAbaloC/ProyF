package com.CarlosA.ProyF.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", nullable = false, length = 15)
    private String nombre;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ejemplo> personas;

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
    public List<Ejemplo> getPersonas() {
        return personas;
    }
    public void setPersonas(List<Ejemplo> personas) {
        this.personas = personas;
    }
    public Company() {  }
    public Company(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
