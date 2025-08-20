package com.hogar.service;

import com.hogar.domain.Taller;
import java.util.List;
import java.util.Optional;

public interface TallerService {
    List<Taller> listarTalleres();
    Taller guardarTaller(Taller taller);
    void eliminarTaller(Integer id);
    void reactivarTaller(Integer id);
    Optional<Taller> buscarPorId(Integer id);
}