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

        double tCambio = 505; // o bien leerlo con @Value("${tCambio}")
        double totalUSD = total / tCambio;

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("totalUSD", totalUSD);

        if (mensaje != null && !mensaje.isEmpty()) {
            model.addAttribute("mensaje", mensaje);
            model.addAttribute("tipoMensaje", tipoMensaje);
        }

        return "carrito/listado";
    }

    @GetMapping("/agregar/{idTaller}")
    public String agregar(@PathVariable Integer idTaller, RedirectAttributes redirectAttrs) {
        boolean agregado = carritoService.agregarTaller(idTaller);

        if (agregado) {
            redirectAttrs.addFlashAttribute("mensaje", "El taller se ha reservado con éxito.");
            redirectAttrs.addFlashAttribute("tipoMensaje", "success");
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "El taller ya fue reservado, solo puede reservarse 1 cupo por persona.");
            redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        }

        return "redirect:/carrito/listado";
    }

    @GetMapping("/quitar/{idItem}")
    public String quitar(@PathVariable Integer idItem, RedirectAttributes redirectAttrs) {
        carritoService.quitarItem(idItem);
        redirectAttrs.addFlashAttribute("mensaje", "El taller fue eliminado del carrito.");
        redirectAttrs.addFlashAttribute("tipoMensaje", "warning");
        return "redirect:/carrito/listado";
    }

    @GetMapping("/facturar")
    public String facturar(RedirectAttributes redirectAttrs) {
        FacturaTaller factura = carritoService.facturar();
        if (factura != null) {
            // Simulación de confirmación de PayPal (reemplazar con integración real)
            redirectAttrs.addFlashAttribute("mensaje", "Pago realizado con éxito.");
            redirectAttrs.addFlashAttribute("tipoMensaje", "success");
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "Error al procesar el pago.");
            redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/carrito/listado";
    }
}