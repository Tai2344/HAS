package com.hogar.controller;

import org.springframework.web.bind.annotation.*;

public class RegistroController {
    @PostMapping("/Registro")
    public String registrarResidente(
            @RequestParam String nombre,
            @RequestParam String cedula,
            @RequestParam String telefono,
            @RequestParam String password,
            @RequestParam(required = false) String padecimientos
    ) {
        
        System.out.println("Registrado: " + nombre + ", " + cedula + ", " + telefono + ", " + padecimientos);
        return "redirect:/Registro?success";
    }

    @GetMapping("/Registro")
    public String mostrarFormularioRegistro() {
        return "hogar/Registro"; 
    }
}