package com.hogar.repository;

import com.hogar.domain.ReservaTaller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaTallerRepository extends JpaRepository<ReservaTaller, Integer> {
    boolean existsByUsuarioIdUsuarioAndTallerIdTaller(Integer idUsuario, Integer idTaller);
    List<ReservaTaller> findByUsuarioIdUsuario(Integer idUsuario);
}