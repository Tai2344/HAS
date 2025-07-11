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
        // Lógica simple de validación (puedes reemplazar con base de datos)
        if (cedula.equals("3-0000-0000") && password.equals("123456")) {
            return "redirect:/inicio"; // Página principal tras login exitoso
        } else {
            model.addAttribute("error", "Credenciales inválidas");
            return "hogar/registro"; // Muestra error en la misma vista
        }
    }

    @GetMapping("/inicio")
    public String paginaInicio() {
        return "hogar/menu"; 
    }
}