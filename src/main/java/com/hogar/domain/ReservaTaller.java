package com.hogar.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "reserva_taller")
public class ReservaTaller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva_taller") 
    private Integer idReserva;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_taller", nullable = false)
    private Taller taller;

    @ManyToOne
    @JoinColumn(name = "id_factura_taller", nullable = true)
    private FacturaTaller facturaTaller;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDate fechaProgramada;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "nombre_taller")
    private String nombreTaller;

    @Column(name = "horario")
    private String horario;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "codigo_participacion")
    private String codigoParticipacion;
}