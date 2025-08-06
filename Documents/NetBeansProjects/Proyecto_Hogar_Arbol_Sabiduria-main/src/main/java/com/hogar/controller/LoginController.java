package com.hogar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public class LoginController { 
    @PostMapping("/login")
    public String login(
            @RequestParam String cedula,
            @RequestParam String password,
            Model model
    ) {
        // Validación de datos
        if (cedula.equals("3-0000-0000") && password.equals("123456")) {
            return "redirect:/inicio";
        } else {
            // Si los datos son erroneos, tira error
            model.addAttribute("error", "Credenciales inválidas");
            return "hogar/Registro"; 
        }
    }

    @GetMapping("/inicio")
    public String paginaInicio() {
        return "hogar/Menu"; 
    }
}