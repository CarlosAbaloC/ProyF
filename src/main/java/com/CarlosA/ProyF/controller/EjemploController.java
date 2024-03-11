package com.CarlosA.ProyF.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.CarlosA.ProyF.model.Company;
import com.CarlosA.ProyF.model.Ejemplo;
import com.CarlosA.ProyF.service.CompaniesService;
import com.CarlosA.ProyF.service.EjemploService;
import com.CarlosA.ProyF.util.ImageUtils;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api")
public class EjemploController {
    @Autowired
    private EjemploService ejemploService;

    @Autowired
    private CompaniesService companiesService;


    @GetMapping("/juego")
    public List<Ejemplo> getAllEjemplos() {
    return ejemploService.getAllEjemplos();
    }

    //Orden inverso
    @Operation(summary = "Trae los juegos ordenado inversamente por id", description = "Trae los juegos ordenado inversamente por id", tags = {"juegos"})
    @GetMapping("/juego/orden")
    public List<Ejemplo> getAllOrdered() {
    return ejemploService.getAllOrdered();
    }

    @Operation(summary = "Crea un juego", description = "Añade un juego a la colección", tags = {"juegos"})
    @ApiResponse(responseCode = "201", description = "Persona añadida")
    @ApiResponse(responseCode = "400", description = "Datos de juego no válidos")
    @PostMapping(value = "/juego", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Ejemplo> createEjemplo(@RequestParam String nombre, @RequestParam Integer anio, 
                                                @RequestParam Long id_Company,
                                                @RequestPart(name="imagen", required=false) MultipartFile imagen) throws IOException {

         /* Es necesario elegir un id de género existente */
         Company company = new Company();
         Optional<Company> optionalCompany = companiesService.getCompanyById(id_Company);
         if (((Optional<?>) optionalCompany).isPresent()) {
             company = optionalCompany.get();
         }
         else {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

        //Ejemplo ejemplo = new Ejemplo(nombre, anio)
        Ejemplo createdEjemplo = ejemploService.createEjemplo(new Ejemplo(nombre, anio, company), imagen);
        return new ResponseEntity<>(createdEjemplo, HttpStatus.CREATED);
    }

    @GetMapping("/juego/{id}")
    public ResponseEntity<Ejemplo> getEjemploById(@PathVariable Long id) {
        Optional<Ejemplo> optionalEjemplo = ejemploService.getEjemploById(id);
        if (((Optional<?>) optionalEjemplo).isPresent()) {
            optionalEjemplo = ejemploService.getEjemploById(id);
            return new ResponseEntity<>(optionalEjemplo.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtiene un Persona", description = "Obtiene un juego dado suid", tags = {"juegos"})
    @Parameter(name = "id", description = "ID de la Persona", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "Persona encontrada")
    @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    @PutMapping(value = "/juego/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Ejemplo> updateEjemplo(@PathVariable Long id, @RequestParam String nombre,
    @RequestParam Integer anio,  @RequestParam Long id_Company,
    @RequestPart(name="imagen", required=false) MultipartFile imagen) throws IOException {

        /* Es necesario elegir un id de género existente */
        Company company = new Company();
        Optional<Company> optionalCompany = companiesService.getCompanyById(id_Company);
        if (((Optional<?>) optionalCompany).isPresent()) {
            company = optionalCompany.get();
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Ejemplo> optionalEjemplo = ejemploService.getEjemploById(id);
        if (((Optional<?>) optionalEjemplo).isPresent()) {
            Ejemplo existingEjemplo = optionalEjemplo.get();
            existingEjemplo.setNombre(nombre);
            existingEjemplo.setAnio(anio);
            existingEjemplo.setCompany(company);
            existingEjemplo.setUpdated_at(LocalDateTime.now());
            existingEjemplo.setFoto(ImageUtils.compressImage(imagen.getBytes()));

            Ejemplo updatedEjemplo = ejemploService.updateEjemplo(existingEjemplo, imagen);
            return new ResponseEntity<>(updatedEjemplo, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/juego/{id}")
    public ResponseEntity<Void> deleteEjemplo(@PathVariable Long id) {
        Optional<Ejemplo> optionalEjemplo = ejemploService.getEjemploById(id);
        if (optionalEjemplo.isPresent()) {
            ejemploService.deleteEjemploById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Definir otros endpoints para POST, PUT, DELETE según sea necesario
    @GetMapping("/juego/nom")
    public ResponseEntity<List<Ejemplo>> getEjemplosPorNombre(@RequestParam String nombre) {
        List<Ejemplo> ejemplos =
       ejemploService.getEjemplosByNombre(nombre);
        if (!ejemplos.isEmpty()) {
        return new ResponseEntity<>(ejemplos, HttpStatus.OK);
        }
        else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       }
       @Operation(summary = "Muestra foto", description = "Obtiene foto de juego dado el id", tags = {"juegos"})
    @Parameter(name = "id", description = "ID del Juego", required = true, example = "13")
    @ApiResponse(responseCode = "200", description = "Foto del juego")
    @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    @GetMapping(value = "/{id}/foto", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> descargarFoto(@PathVariable Long id) {
        byte[] foto = ejemploService.descargarFoto(id);
        if ( foto != null ) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(foto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
   