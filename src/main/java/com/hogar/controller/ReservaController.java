package com.hogar.controller;

import com.hogar.repository.ReservaTallerRepository;
import com.hogar.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final CarritoService carritoService;
    private final ReservaTallerRepository reservaTallerRepository; // Corrección de ventaTallerRepository

    @GetMapping("/listado")
    public String listado(Model model) {
        Integer idUsuario = carritoService.getCarritoUsuario().getUsuario().getIdUsuario();
        var reservas = reservaTallerRepository.findByUsuarioIdUsuario(idUsuario);
        model.addAttribute("reservas", reservas);
        return "reservas/listado";
    }
}