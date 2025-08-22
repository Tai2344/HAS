package com.hogar.repository;

import com.hogar.domain.CarritoItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Integer> {

    List<CarritoItem> findByCarrito_IdCarrito(Integer idCarrito);

    boolean existsByCarrito_IdCarritoAndTallerEs_IdTaller(Integer idCarrito, Integer idTallerEs);

    boolean existsByCarrito_IdCarritoAndTallerEn_IdTaller(Integer idCarrito, Integer idTallerEn);
}
