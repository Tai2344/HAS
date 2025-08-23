package com.hogar.controller;

import com.hogar.domain.FacturaTaller;
import com.hogar.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;
    private final MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model,
                          @ModelAttribute("mensaje") String mensaje,
                          @ModelAttribute("tipoMensaje") String tipoMensaje,
                          Locale locale) {
        var items = carritoService.getItems();
        double total = carritoService.calcularTotal(items);
        double tCambio = 505; // aquí podrías usar @Value desde application.properties
        double totalUSD = total / tCambio;

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("totalUSD", totalUSD);

        if (mensaje != null && !mensaje.isEmpty()) {
            // el flash ya dejó el mensaje resuelto en el controlador; lo dejamos para la vista
            model.addAttribute("mensaje", mensaje);
            model.addAttribute("tipoMensaje", tipoMensaje);
        }

        // resolver el título aquí con MessageSource para internacionalización
        model.addAttribute("tituloListado", messageSource.getMessage("carritocontrol.tituloListado", null, locale));

        return "carrito/listado";
    }

    @GetMapping("/agregar/{idTaller}/{idioma}")
    public String agregar(@PathVariable Integer idTaller,
                          @PathVariable String idioma,
                          RedirectAttributes redirectAttrs,
                          Locale locale) {
        boolean agregado = carritoService.agregarTaller(idTaller, idioma);

        if (agregado) {
            String texto = messageSource.getMessage("carritocontrol.agregar.exito", null, locale);
            redirectAttrs.addFlashAttribute("mensaje", texto);
            redirectAttrs.addFlashAttribute("tipoMensaje", "success");
        } else {
            String texto = messageSource.getMessage("carritocontrol.agregar.yareservado", null, locale);
            redirectAttrs.addFlashAttribute("mensaje", texto);
            redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        }

        return "redirect:/carrito/listado";
    }

    @GetMapping("/quitar/{idItem}")
    public String quitar(@PathVariable Integer idItem, RedirectAttributes redirectAttrs, Locale locale) {
        carritoService.quitarItem(idItem);
        String texto = messageSource.getMessage("carritocontrol.quitar.exito", null, locale);
        redirectAttrs.addFlashAttribute("mensaje", texto);
        redirectAttrs.addFlashAttribute("tipoMensaje", "warning");
        return "redirect:/carrito/listado";
    }

    @GetMapping("/facturar")
    public String facturar(RedirectAttributes redirectAttrs, Locale locale) {
        FacturaTaller factura = carritoService.facturar();
        if (factura != null) {
            String texto = messageSource.getMessage("carritocontrol.facturar.exito", null, locale);
            redirectAttrs.addFlashAttribute("mensaje", texto);
            redirectAttrs.addFlashAttribute("tipoMensaje", "success");
        } else {
            String texto = messageSource.getMessage("carritocontrol.facturar.error", null, locale);
            redirectAttrs.addFlashAttribute("mensaje", texto);
            redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/carrito/listado";
    }
}