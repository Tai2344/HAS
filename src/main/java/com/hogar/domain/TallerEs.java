package com.hogar.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "taller_es")
public class TallerEs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_taller")
    private Integer idTaller;

    private String codigo;
    private String nombre;
    private String descripcion;
    private String horario;
    private Double precio;
    private Boolean activo;
}
