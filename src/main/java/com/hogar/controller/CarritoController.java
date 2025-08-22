package com.hogar.controller;

import com.hogar.domain.FacturaTaller;
import com.hogar.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping("/listado")
    public String listado(Model model,
                          @ModelAttribute("mensaje") String mensaje,
                          @ModelAttribute("tipoMensaje") String tipoMensaje) {
        var items = carritoService.getItems();
        double total = carritoService.calcularTotal(items);
        double tCambio = 505; // aquí podrías usar @Value desde application.properties
        double totalUSD = total / tCambio;

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("totalUSD", totalUSD);

        if (mensaje != null && !mensaje.isEmpty()) {
            model.addAttribute("mensaje", mensaje);
            model.addAttribute("tipoMensaje", tipoMensaje);
        }

        // Internacionalización para título de la página
        model.addAttribute("tituloListado", "#{carritocontrol.tituloListado}");

        return "carrito/listado";
    }

    @GetMapping("/agregar/{idTaller}/{idioma}")
    public String agregar(@PathVariable Integer idTaller,
                          @PathVariable String idioma,
                          RedirectAttributes redirectAttrs) {
        boolean agregado = carritoService.agregarTaller(idTaller, idioma);

        if (agregado) {
            redirectAttrs.addFlashAttribute("mensaje", "#{carritocontrol.agregar.exito}");
            redirectAttrs.addFlashAttribute("tipoMensaje", "success");
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "#{carritocontrol.agregar.yareservado}");
            redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        }

        return "redirect:/carrito/listado";
    }

    @GetMapping("/quitar/{idItem}")
    public String quitar(@PathVariable Integer idItem, RedirectAttributes redirectAttrs) {
        carritoService.quitarItem(idItem);
        redirectAttrs.addFlashAttribute("mensaje", "#{carritocontrol.quitar.exito}");
        redirectAttrs.addFlashAttribute("tipoMensaje", "warning");
        return "redirect:/carrito/listado";
    }

    @GetMapping("/facturar")
    public String facturar(RedirectAttributes redirectAttrs) {
        FacturaTaller factura = carritoService.facturar();
        if (factura != null) {
            redirectAttrs.addFlashAttribute("mensaje", "#{carritocontrol.facturar.exito}");
            redirectAttrs.addFlashAttribute("tipoMensaje", "success");
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "#{carritocontrol.facturar.error}");
            redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/carrito/listado";
    }
}