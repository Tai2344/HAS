package com.hogar.repository;

import com.hogar.domain.Usuario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCedula(String cedula);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.agendoCita = true WHERE u.idUsuario = :idUsuario")
    void marcarReserva(@Param("idUsuario") Integer idUsuario);
}