package com.hogar.controller;

import com.hogar.domain.ReservaTaller;
import com.hogar.domain.Usuario;
import com.hogar.service.AdministracionService;
import com.hogar.service.TallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administracion")
public class AdministracionController {

    @Autowired
    private AdministracionService administracionService;

    @Autowired
    private TallerService tallerService;

    @GetMapping("/listado")
    @PreAuthorize("hasRole('ADMIN')")
    public String listarAdministracion(Model model) {
        model.addAttribute("usuarios", administracionService.listarUsuarios());
        model.addAttribute("talleres", administracionService.listarTalleres());
        return "administracion/listado";
    }

    @GetMapping("/editarUsuario/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        model.addAttribute("usuario", administracionService.encontrarUsuarioPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        return "administracion/editarUsuario";
    }

    @PostMapping("/editarUsuario")
    @PreAuthorize("hasRole('ADMIN')")
    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        administracionService.actualizarUsuario(usuario);
        return "redirect:/administracion/listado";
    }

    @GetMapping("/reservas")
    @PreAuthorize("hasRole('ADMIN')")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", administracionService.listarReservas());
        return "administracion/reservas";
    }

    @GetMapping("/desactivarTaller/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String desactivarTaller(@PathVariable Integer id) {
        tallerService.eliminarTaller(id); 
        return "redirect:/administracion/listado";
    }

    @GetMapping("/reactivarTaller/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String reactivarTaller(@PathVariable Integer id) {
        tallerService.reactivarTaller(id);
        return "redirect:/administracion/listado";
    }
}