package com.hogar.repository;

import com.hogar.domain.TallerEs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TallerEsRepository extends JpaRepository<TallerEs, Integer> {
    List<TallerEs> findByActivoTrue();
}
