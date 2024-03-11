package com.CarlosA.ProyF.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CarlosA.ProyF.controller.CompaniesController;
import com.CarlosA.ProyF.exceptions.CompaniesNotFoundException;
import com.CarlosA.ProyF.model.Company;
import com.CarlosA.ProyF.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;
@Service
public class CompaniesService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {

        return Optional.ofNullable(companyRepository.findById(id).orElseThrow(
                () -> new CompaniesNotFoundException("No se ha encontrado el company con id: " + id)
        ));
    }
}
