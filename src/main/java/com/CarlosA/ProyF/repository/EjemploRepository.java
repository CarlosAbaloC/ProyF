package com.CarlosA.ProyF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CarlosA.ProyF.model.Ejemplo;

public interface EjemploRepository extends JpaRepository<Ejemplo, Long>{
    
    // Aquí puedes agregar métodos personalizados si es necesario
    List<Ejemplo> findByNombreContainingIgnoreCase(String nombre);  

}
