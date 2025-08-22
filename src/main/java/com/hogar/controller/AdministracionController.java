package com.hogar.controller;

import com.hogar.domain.Usuario;
import com.hogar.domain.TallerEs;
import com.hogar.domain.TallerEn;
import com.hogar.service.AdministracionService;
import com.hogar.service.TallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/administracion")
@RequiredArgsConstructor
public class AdministracionController {

    private final AdministracionService administracionService;
    private final TallerService tallerService;

    // Panel principal
    @GetMapping("/listado")
    @PreAuthorize("hasRole('ADMIN')")
    public String listarAdministracion(Model model) {
        model.addAttribute("usuarios", administracionService.listarUsuarios());
        model.addAttribute("talleresEs", tallerService.listarTalleresEs());
        model.addAttribute("talleresEn", tallerService.listarTalleresEn());
        return "administracion/listado";
    }

    // --- USUARIOS ---
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

    // --- TALLER ESPAÑOL (gestión agregar y editar en uno) ---
    @GetMapping("/gestionarTallerEs")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormGestionarTallerEs(@RequestParam(value = "id", required = false) Integer id, Model model) {
        TallerEs tallerEs = (id != null) ? tallerService.obtenerTallerEsPorId(id) : new TallerEs();
        model.addAttribute("tallerEs", tallerEs);
        return "administracion/gestionarTallerEs";
    }

    @PostMapping("/gestionarTallerEs")
    @PreAuthorize("hasRole('ADMIN')")
    public String gestionarTallerEs(@ModelAttribute TallerEs tallerEs) {
        tallerService.guardarTallerEs(tallerEs);
        return "redirect:/administracion/listado";
    }

    @GetMapping("/desactivarTallerEs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String desactivarTallerEs(@PathVariable Integer id) {
        tallerService.eliminarTallerEs(id);
        return "redirect:/administracion/listado";
    }

    @GetMapping("/reactivarTallerEs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String reactivarTallerEs(@PathVariable Integer id) {
        tallerService.reactivarTallerEs(id);
        return "redirect:/administracion/listado";
    }

    @GetMapping("/eliminarTallerEs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarTallerEs(@PathVariable Integer id) {
        tallerService.eliminarFisicoTallerEs(id);
        return "redirect:/administracion/listado";
    }

    // --- TALLER INGLÉS (gestión agregar y editar en uno) ---
    @GetMapping("/gestionarTallerEn")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormGestionarTallerEn(@RequestParam(value = "id", required = false) Integer id, Model model) {
        TallerEn tallerEn = (id != null) ? tallerService.obtenerTallerEnPorId(id) : new TallerEn();
        model.addAttribute("tallerEn", tallerEn);
        return "administracion/gestionarTallerEn";
    }

    @PostMapping("/gestionarTallerEn")
    @PreAuthorize("hasRole('ADMIN')")
    public String gestionarTallerEn(@ModelAttribute TallerEn tallerEn) {
        tallerService.guardarTallerEn(tallerEn);
        return "redirect:/administracion/listado";
    }

    @GetMapping("/desactivarTallerEn/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String desactivarTallerEn(@PathVariable Integer id) {
        tallerService.eliminarTallerEn(id);
        return "redirect:/administracion/listado";
    }

    @GetMapping("/reactivarTallerEn/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String reactivarTallerEn(@PathVariable Integer id) {
        tallerService.reactivarTallerEn(id);
        return "redirect:/administracion/listado";
    }

    @GetMapping("/eliminarTallerEn/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarTallerEn(@PathVariable Integer id) {
        tallerService.eliminarFisicoTallerEn(id);
        return "redirect:/administracion/listado";
    }
}