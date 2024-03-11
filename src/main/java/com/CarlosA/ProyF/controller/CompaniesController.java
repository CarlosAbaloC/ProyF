package com.CarlosA.ProyF.controller;


import com.CarlosA.ProyF.service.CompaniesService;
import com.CarlosA.ProyF.model.Company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CompaniesController {
    @Autowired
    private CompaniesService companiesService;


    
    @GetMapping("/companiesview")
    public ModelAndView listado(Model modelo) throws UnsupportedEncodingException {
        List<Company> companies = getAllCompanies();

        modelo.addAttribute("listaCompanies", companies);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listadogen.html");
        return modelAndView;
    }

    @Operation(summary = "Obtiene todas los Géneros", description = "Obtiene una lista de Géneros", tags = {"companies"})
    @ApiResponse(responseCode = "200", description = "Lista de Géneros")
    @GetMapping("/company")
    public List<Company> getAllCompanies() {
        return companiesService.getAllCompanies();
    }

    @Operation(summary = "Obtiene un Género", description = "Obtiene un género dado su id", tags = {"companies"})
    @Parameter(name = "id", description = "ID del Género", required = true, example = "1")
    @ApiResponse(responseCode = "200", description = "Género encontrado")
    @ApiResponse(responseCode = "404", description = "Género no encontrado")
    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Optional<Company> optionalCompany = companiesService.getCompanyById(id);

        if (((Optional<?>) optionalCompany).isPresent()) {
            optionalCompany = companiesService.getCompanyById(id);
            return new ResponseEntity<>(optionalCompany.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
