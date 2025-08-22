package com.hogar.service;

import com.hogar.domain.Carrito;
import com.hogar.domain.CarritoItem;
import com.hogar.domain.FacturaTaller;
import com.hogar.domain.ReservaTaller;
import com.hogar.domain.TallerEn;
import com.hogar.domain.TallerEs;
import com.hogar.domain.Usuario;
import com.hogar.repository.CarritoItemRepository;
import com.hogar.repository.CarritoRepository;
import com.hogar.repository.FacturaTallerRepository;
import com.hogar.repository.ReservaTallerRepository;
import com.hogar.repository.TallerEnRepository;
import com.hogar.repository.TallerEsRepository;
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
    private final TallerEsRepository tallerEsRepository;
    private final TallerEnRepository tallerEnRepository;
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

    public boolean agregarTaller(Integer idTaller, String idioma) {
        Carrito carrito = getCarritoUsuario();

        if (idioma.equalsIgnoreCase("es")) {
            TallerEs taller = tallerEsRepository.findById(idTaller).orElseThrow();

            // CORREGIDO: primero idUsuario, luego idTallerEs
            if (reservaTallerRepository.existsByUsuario_IdUsuarioAndTallerEs_IdTaller(carrito.getUsuario().getIdUsuario(), idTaller)) {
                return false;
            }

            if (!carritoItemRepository.existsByCarrito_IdCarritoAndTallerEs_IdTaller(carrito.getIdCarrito(), idTaller)) {
                CarritoItem item = new CarritoItem();
                item.setCarrito(carrito);
                item.setTallerEs(taller);
                item.setCantidad(1);
                carritoItemRepository.save(item);
            }
        } else {
            TallerEn taller = tallerEnRepository.findById(idTaller).orElseThrow();

            // CORREGIDO: primero idUsuario, luego idTallerEn
            if (reservaTallerRepository.existsByUsuario_IdUsuarioAndTallerEn_IdTaller(carrito.getUsuario().getIdUsuario(), idTaller)) {
                return false;
            }

            if (!carritoItemRepository.existsByCarrito_IdCarritoAndTallerEn_IdTaller(carrito.getIdCarrito(), idTaller)) {
                CarritoItem item = new CarritoItem();
                item.setCarrito(carrito);
                item.setTallerEn(taller);
                item.setCantidad(1);
                carritoItemRepository.save(item);
            }
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

    public double calcularTotal(List<CarritoItem> items) {
        return items.stream().mapToDouble(item -> {
            if (item.getTallerEs() != null) {
                return item.getTallerEs().getPrecio() * item.getCantidad();
            } else {
                return item.getTallerEn().getPrecio() * item.getCantidad();
            }
        }).sum();
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
            LocalDate fecha;
            ReservaTaller rt = new ReservaTaller();
            rt.setUsuario(carrito.getUsuario());
            rt.setFacturaTaller(factura);
            rt.setCantidad(item.getCantidad());

            if (item.getTallerEs() != null) {
                fecha = calcularProximaFecha(item.getTallerEs().getHorario());
                rt.setTallerEs(item.getTallerEs());
                rt.setNombreTaller(item.getTallerEs().getNombre());
                rt.setHorario(item.getTallerEs().getHorario());
                rt.setPrecio(item.getTallerEs().getPrecio());
                rt.setCodigoParticipacion(generarCodigoParticipacion(item.getTallerEs().getNombre()));
            } else {
                fecha = calcularProximaFecha(item.getTallerEn().getHorario());
                rt.setTallerEn(item.getTallerEn());
                rt.setNombreTaller(item.getTallerEn().getNombre());
                rt.setHorario(item.getTallerEn().getHorario());
                rt.setPrecio(item.getTallerEn().getPrecio());
                rt.setCodigoParticipacion(generarCodigoParticipacion(item.getTallerEn().getNombre()));
            }

            rt.setFechaProgramada(fecha);
            reservaTallerRepository.save(rt);
        }

        carritoItemRepository.deleteAll(items);
        return factura;
    }
}