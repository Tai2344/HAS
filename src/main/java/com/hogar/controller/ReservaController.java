package com.hogar.controller;

import com.hogar.repository.ReservaTallerRepository;
import com.hogar.repository.UsuarioRepository;
import com.hogar.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final CarritoService carritoService;
    private final ReservaTallerRepository reservaTallerRepository;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/listado")
    public String listado(Model model) {
        Integer idUsuario = carritoService.getCarritoUsuario().getUsuario().getIdUsuario();
        var reservas = reservaTallerRepository.findByUsuario_IdUsuario(idUsuario);
        model.addAttribute("reservas", reservas);
        return "reservas/listado";
    }

    @PostMapping("/agendar")
    public String agendar(Model model) {
        var usuario = carritoService.getCarritoUsuario().getUsuario();
        usuario.setAgendoCita(true); 
        usuarioRepository.save(usuario);

        model.addAttribute("mensajeExito", "#{reservacontrol.mensajeExito}");
        return "hogar/galeria"; 
    }
}