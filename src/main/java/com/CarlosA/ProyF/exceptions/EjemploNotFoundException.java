package com.CarlosA.ProyF.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class EjemploNotFoundException extends EjemploException {
    public EjemploNotFoundException(String mensaje) {
        super(mensaje);
    }
}
