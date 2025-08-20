package com.hogar.repository;

import com.hogar.domain.Taller;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TallerRepository extends JpaRepository<Taller, Integer> {
    List<Taller> findByActivoTrue();  // Agregado: Para listar solo activos
}