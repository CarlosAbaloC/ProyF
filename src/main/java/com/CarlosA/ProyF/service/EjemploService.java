package com.CarlosA.ProyF.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.CarlosA.ProyF.exceptions.EjemploBadRequestException;
import com.CarlosA.ProyF.exceptions.EjemploException;
import com.CarlosA.ProyF.exceptions.EjemploNotFoundException;
import com.CarlosA.ProyF.model.Company;
import com.CarlosA.ProyF.model.Ejemplo;
import com.CarlosA.ProyF.repository.EjemploRepository;
import com.CarlosA.ProyF.util.ImageUtils;

import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

//El controlador hace las cosas a traves de los servicios, esto
@Service
public class EjemploService {
    @Autowired
    private EjemploRepository ejemploRepository;
    public List<Ejemplo> getAllEjemplos() {
        return ejemploRepository.findAll();
    }
    public List<Ejemplo> getAllOrdered() {
        return ejemploRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
    public Ejemplo createEjemplo(Ejemplo ejemplo, MultipartFile file) throws IOException {
        if (ejemplo.getNombre() == null || ejemplo.getNombre().isEmpty())
            throw new EjemploBadRequestException("Debe introducirse el nombre");
        if (ejemplo.getAnio() == null || ejemplo.getAnio() <= 0)
            throw new EjemploBadRequestException("Debe introducirse la anio y debe ser mayor que 0");
        
        Long idCompany = ejemplo.getCompany().getId();

        if (idCompany == null || idCompany <= 0 || idCompany > 3)
            throw new EjemploBadRequestException("Debe introducirse valor de género entre 1 y 3");

        String company;
        if ( idCompany == 1)
            company = "Bethesda";
        else if ( idCompany == 2)
            company = "Blizzard";
        else
            company = "Riot";


        Ejemplo ejemplosave = new Ejemplo(ejemplo.getNombre(), ejemplo.getAnio(), new Company(idCompany, company));
        if (!file.isEmpty()) {
            ejemplosave.setImagen(file.getOriginalFilename());
            ejemplosave.setFoto(ImageUtils.compressImage(file.getBytes())); // Almacena en BD el binario de la foto
            // El resto de líneas es para almacenar la imagen en disco
            Path dirImg = Paths.get("src//main//resources//static//img");
            String rutaAbsoluta = dirImg.toFile().getAbsolutePath();
            try {
                byte[] bytesImg = file.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" +
                file.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException e) {
                throw new EjemploException("Error de escritura");
            }
        }
        else
            throw new EjemploBadRequestException("Debe introducirse el fichero imagen");
        
        return ejemploRepository.save(ejemplosave);
    }
       
    public Optional<Ejemplo> getEjemploById(Long id) {
        // return ejemploRepository.findById(id);
        return Optional.ofNullable(ejemploRepository.findById(id).orElseThrow(
        () -> new EjemploNotFoundException("No se ha encontrado la persona con id: " + id)
        ));
       }
       
    public Ejemplo updateEjemplo(Ejemplo ejemplo, MultipartFile file) throws IOException {
    if (ejemplo.getNombre() == null || ejemplo.getNombre().isEmpty())
        throw new EjemploBadRequestException("Debe introducirse el nombre");
    if (ejemplo.getAnio() == null || ejemplo.getAnio() <= 0)
        throw new EjemploBadRequestException("Debe introducirse la anio y debe ser mayor que 0");
    if (!file.isEmpty()) {
        ejemplo.setImagen(file.getOriginalFilename());
        ejemplo.setFoto(ImageUtils.compressImage(file.getBytes())); // Almacena en BD el binario de la foto
        // El resto de líneas es para almacenar la imagen en disco
        Path dirImg = Paths.get("src//main//resources//static//img");
        String rutaAbsoluta = dirImg.toFile().getAbsolutePath();
        try {
            byte[] bytesImg = file.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" +
            file.getOriginalFilename());
            Files.write(rutaCompleta, bytesImg);
        } catch (IOException e) {
            throw new EjemploException("Error de escritura");
        }
    }
    else
        throw new EjemploBadRequestException("Debe introducirse el fichero imagen");
    
        return ejemploRepository.save(ejemplo);
    }

    public void deleteEjemploById(Long id) {
        ejemploRepository.deleteById(id);
    }
    // Otros métodos para operaciones específicas
    public List<Ejemplo> getEjemplosByNombre(String nombre) { 
        return ejemploRepository.findByNombreContainingIgnoreCase(nombre); 
    }

    public byte[] descargarFoto(Long id) {
        Ejemplo ejemplo = ejemploRepository.findById(id).orElse(null);
        return ejemplo != null ? ImageUtils.decompressImage(ejemplo.getFoto()) : null;
    }
    
}
