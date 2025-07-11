package com.hogar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

public class RegistroController {
    @PostMapping("/registro")
    public String registrarResidente(
            @RequestParam String nombre,
            @RequestParam String cedula,
            @RequestParam String telefono,
            @RequestParam String password,
            @RequestParam(required = false) String padecimientos
    ) {
        
        System.out.println("Registrado: " + nombre + ", " + cedula + ", " + telefono + ", " + padecimientos);
        return "redirect:/registro?success";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "hogar/registro"; // Carga el archivo templates/hogar/registro.html
    }
}