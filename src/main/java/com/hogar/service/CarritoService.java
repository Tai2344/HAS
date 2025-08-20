package com.hogar.service;

import com.hogar.domain.Carrito;
import com.hogar.domain.CarritoItem;
import com.hogar.domain.FacturaTaller;
import com.hogar.domain.ReservaTaller;
import com.hogar.domain.Taller;
import com.hogar.domain.Usuario;
import com.hogar.repository.CarritoItemRepository;
import com.hogar.repository.CarritoRepository;
import com.hogar.repository.FacturaTallerRepository;
import com.hogar.repository.ReservaTallerRepository;
import com.hogar.repository.TallerRepository;
import com.hogar.repository.UsuarioRepository;
import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final TallerRepository tallerRepository;
    private final FacturaTallerRepository facturaTallerRepository;
    private final ReservaTallerRepository reservaTallerRepository;

    public Carrito getCarritoUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String cedula = authentication.getName();
        Usuario usuario = usuarioRepository.findByCedula(cedula).orElseThrow();
        return carritoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    nuevo.setUsuario(usuario);
                    return carritoRepository.save(nuevo);
                });
    }

    public boolean agregarTaller(Integer idTaller) {
        Carrito carrito = getCarritoUsuario();

        if (reservaTallerRepository.existsByUsuarioIdUsuarioAndTallerIdTaller(carrito.getUsuario().getIdUsuario(), idTaller)) {
            return false;
        }

        if (!carritoItemRepository.existsByCarrito_IdCarritoAndTaller_IdTaller(carrito.getIdCarrito(), idTaller)) {
            Taller taller = tallerRepository.findById(idTaller).orElseThrow();
            CarritoItem item = new CarritoItem();
            item.setCarrito(carrito);
            item.setTaller(taller);
            item.setCantidad(1);
            carritoItemRepository.save(item);
        }
        return true;
    }

    public List<CarritoItem> getItems() {
        Carrito carrito = getCarritoUsuario();
        return carritoItemRepository.findByCarrito_IdCarrito(carrito.getIdCarrito());
    }

    public void quitarItem(Integer idItem) {
        CarritoItem item = carritoItemRepository.findById(idItem)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        carritoItemRepository.delete(item);
    }

    private LocalDate calcularProximaFecha(String diaSemana) {
        DayOfWeek objetivo;
        switch (diaSemana.toLowerCase(Locale.ROOT)) {
            case "lunes": objetivo = DayOfWeek.MONDAY; break;
            case "martes": objetivo = DayOfWeek.TUESDAY; break;
            case "miércoles":
            case "miercoles": objetivo = DayOfWeek.WEDNESDAY; break;
            case "jueves": objetivo = DayOfWeek.THURSDAY; break;
            case "viernes": objetivo = DayOfWeek.FRIDAY; break;
            case "sábado":
            case "sabado": objetivo = DayOfWeek.SATURDAY; break;
            case "domingo": objetivo = DayOfWeek.SUNDAY; break;
            default: return LocalDate.now();
        }
        LocalDate base = LocalDate.now();
        int diff = (objetivo.getValue() - base.getDayOfWeek().getValue() + 7) % 7;
        return base.plusDays(diff);
    }

    public double calcularTotal(List<CarritoItem> items) { // Cambiado a public
        return items.stream().mapToDouble(item -> item.getTaller().getPrecio() * item.getCantidad()).sum();
    }

    private String generarCodigoParticipacion(String nombreTaller) {
        SecureRandom random = new SecureRandom();
        String base = nombreTaller.substring(0, Math.min(3, nombreTaller.length())).toUpperCase() + random.nextInt(1000);
        return base;
    }

    public FacturaTaller facturar() {
        Carrito carrito = getCarritoUsuario();
        List<CarritoItem> items = carritoItemRepository.findByCarrito_IdCarrito(carrito.getIdCarrito());
        double total = calcularTotal(items);
        if (items.isEmpty()) {
            return null;
        }
        FacturaTaller factura = FacturaTaller.builder()
                .idUsuario(carrito.getUsuario().getIdUsuario())
                .fecha(LocalDateTime.now())
                .total(total)
                .estado("PAGADO")
                .build();

        facturaTallerRepository.save(factura);

        for (CarritoItem item : items) {
            LocalDate fecha = calcularProximaFecha(item.getTaller().getHorario());
            ReservaTaller rt = new ReservaTaller();
            rt.setUsuario(carrito.getUsuario());
            rt.setTaller(item.getTaller());
            rt.setFacturaTaller(factura);
            rt.setNombreTaller(item.getTaller().getNombre());
            rt.setHorario(item.getTaller().getHorario());
            rt.setPrecio(item.getTaller().getPrecio());
            rt.setCantidad(item.getCantidad());
            rt.setCodigoParticipacion(generarCodigoParticipacion(item.getTaller().getNombre()));
            rt.setFechaProgramada(fecha);
            reservaTallerRepository.save(rt);
        }

        carritoItemRepository.deleteAll(items);
        return factura;
    }
} 