package com.hogar.repository;

import com.hogar.domain.TallerEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TallerEnRepository extends JpaRepository<TallerEn, Integer> {
    List<TallerEn> findByActivoTrue();
}
