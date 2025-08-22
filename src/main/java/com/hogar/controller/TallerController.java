package com.hogar.controller;

import com.hogar.service.TallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// Acepta ambos prefijos: /talleres y /taller
@RequestMapping({"/talleres", "/taller"})
@RequiredArgsConstructor
public class TallerController {

    private final TallerService tallerService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("talleresEs", tallerService.listarTalleresEs());
        model.addAttribute("talleresEn", tallerService.listarTalleresEn());
        return "taller/listado";
    }
}