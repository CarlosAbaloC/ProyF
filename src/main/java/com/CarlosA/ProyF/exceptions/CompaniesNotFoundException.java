package com.CarlosA.ProyF.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class CompaniesNotFoundException extends CompanyException{
    public CompaniesNotFoundException(String mensaje) {
        super(mensaje);
    }
}
