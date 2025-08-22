package com.hogar.service;

import com.hogar.domain.TallerEs;
import com.hogar.domain.TallerEn;
import java.util.List;

public interface TallerService {

    // Español
    List<TallerEs> listarTalleresEs();

    void guardarTallerEs(TallerEs taller);

    void eliminarTallerEs(Integer id);

    void reactivarTallerEs(Integer id);

    // Inglés
    List<TallerEn> listarTalleresEn();

    void guardarTallerEn(TallerEn taller);

    void eliminarTallerEn(Integer id);

    void reactivarTallerEn(Integer id);

    TallerEs obtenerTallerEsPorId(Integer id);

    void eliminarFisicoTallerEs(Integer id); // elimina de la BD

    TallerEn obtenerTallerEnPorId(Integer id);

    void eliminarFisicoTallerEn(Integer id); // elimina de la BD
}
