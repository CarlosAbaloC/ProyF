package com.CarlosA.ProyF.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
public class ControladorSaludo {
    @RequestMapping("/")
    String saludo() {
        return "Hola mundo";
    }
    
}
