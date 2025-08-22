package com.hogar.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "carrito_item")
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCarritoItem;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "id_taller_es", nullable = true)
    private TallerEs tallerEs;

    @ManyToOne
    @JoinColumn(name = "id_taller_en", nullable = true)
    private TallerEn tallerEn;
    private Integer cantidad = 1;
}
