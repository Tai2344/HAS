package com.hogar.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factura_taller")
public class FacturaTaller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura_taller")
    private Integer idFacturaTaller;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    private LocalDateTime fecha;

    private Double total;

    private String estado;
}