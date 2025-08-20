package com.hogar.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "taller")
public class Taller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_taller")
    private Integer idTaller;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "horario", nullable = false)
    private String horario;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "icono")
    private String icono;

    @OneToMany(mappedBy = "taller", cascade = CascadeType.ALL) // Quitamos CascadeType.REMOVE si está
    private List<ReservaTaller> reservas;
}