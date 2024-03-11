package com.CarlosA.ProyF.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.CarlosA.ProyF.model.Ejemplo;
import com.CarlosA.ProyF.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

}
