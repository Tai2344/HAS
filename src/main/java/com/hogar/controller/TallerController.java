package com.hogar.controller;

import com.hogar.domain.Taller;
import com.hogar.service.TallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/taller")
public class TallerController {

    @Autowired
    private TallerService tallerService;

    @Value("${tCambio:505}")
    private double tCambio;

    @GetMapping("/listado")
    public String listarTalleres(Model model) {
        model.addAttribute("talleres", tallerService.listarTalleres());
        model.addAttribute("tCambio", tCambio);
        return "taller/listado";
    }

    @GetMapping("/nuevo")
    public String nuevoTaller(Model model) {
        model.addAttribute("taller", new Taller());
        return "talleres/editar";
    }

    @PostMapping("/guardar")
    public String guardarTaller(@ModelAttribute Taller taller) {
        tallerService.guardarTaller(taller);
        return "redirect:/taller/listado";
    }
}