package com.hogar.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.hogar.service.CarritoService;
import com.hogar.service.PaypalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
@Slf4j
public class PaypalController {

    private final CarritoService carritoService;
    private final PaypalService paypalService;

    @PostMapping("/facturar")
    public RedirectView createPayment(
            @RequestParam("total") double total
    ) {
        if (total > 0) {
            try {
                String urlCancel = "http://localhost:3001/payment/cancel";
                String urlSuccess = "http://localhost:3001/payment/success";

                Payment payment = paypalService.createPayment(
                        total,
                        "USD",
                        "paypal",
                        "sale",
                        "Facturación de talleres",
                        urlCancel,
                        urlSuccess);

                for (Links links : payment.getLinks()) {
                    if ("approval_url".equals(links.getRel())) {
                        return new RedirectView(links.getHref());
                    }
                }
            } catch (PayPalRESTException e) {
                log.error("Error en pago: ", e);
            }
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            RedirectAttributes redirectAttrs) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if ("approved".equalsIgnoreCase(payment.getState())) {
                // Guardar factura y generar códigos de participación
                carritoService.facturar();

                redirectAttrs.addFlashAttribute("mensaje", "¡Pago realizado con éxito!");
                redirectAttrs.addFlashAttribute("tipoMensaje", "success");
                return "redirect:/carrito/listado";
            }
        } catch (PayPalRESTException e) {
            redirectAttrs.addFlashAttribute("mensaje", "Error al procesar el pago.");
            redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/carrito/listado";
    }

    @GetMapping("/cancel")
    public String paymentCancel(RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("mensaje", "Pago cancelado.");
        redirectAttrs.addFlashAttribute("tipoMensaje", "warning");
        return "redirect:/carrito/listado";
    }

    @GetMapping("/error")
    public String paymentError(RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("mensaje", "Ocurrió un error en el pago.");
        redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        return "redirect:/carrito/listado";
    }
}