package com.hogar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HogarController {

@GetMapping("/menu")
public String mostrarMenu(Model model) {
    model.addAttribute("activePage", "menu");
    return "hogar/menu";
}

@GetMapping("/registro-inicio")
public String mostrarRegistroInicio() {
    return "registro-inicio";
}
}
