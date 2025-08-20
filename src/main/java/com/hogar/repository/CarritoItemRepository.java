package com.hogar.repository;

import com.hogar.domain.CarritoItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Integer> {
    boolean existsByCarrito_IdCarritoAndTaller_IdTaller(Integer idCarrito, Integer idTaller);

    List<CarritoItem> findByCarrito_IdCarrito(Integer idCarrito);
}