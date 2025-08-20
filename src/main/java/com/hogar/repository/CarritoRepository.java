package com.hogar.repository;

import com.hogar.domain.Carrito;
import com.hogar.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByUsuario(Usuario usuario);
}
