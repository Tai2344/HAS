package com.hogar.repository;

import com.hogar.domain.ReservaTaller;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaTallerRepository extends JpaRepository<ReservaTaller, Integer> {

    // Verifica si el usuario ya reservó un taller en español
    boolean existsByUsuario_IdUsuarioAndTallerEs_IdTaller(Integer idUsuario, Integer idTallerEs);

    // Verifica si el usuario ya reservó un taller en inglés
    boolean existsByUsuario_IdUsuarioAndTallerEn_IdTaller(Integer idUsuario, Integer idTallerEn);

    // Para ReservaController (listar reservas por usuario)
    List<ReservaTaller> findByUsuario_IdUsuario(Integer idUsuario);
}