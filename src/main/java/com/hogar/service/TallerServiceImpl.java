package com.hogar.service;

import com.hogar.domain.TallerEs;
import com.hogar.domain.TallerEn;
import com.hogar.repository.TallerEsRepository;
import com.hogar.repository.TallerEnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TallerServiceImpl implements TallerService {

    private final TallerEsRepository tallerEsRepository;
    private final TallerEnRepository tallerEnRepository;

    // ------- Español -------
    @Override
    public List<TallerEs> listarTalleresEs() {
        return tallerEsRepository.findAll();
    }

    public void guardarTallerEs(TallerEs taller) {
        tallerEsRepository.save(taller);
    }

    @Override
    public void eliminarTallerEs(Integer id) {
        TallerEs taller = tallerEsRepository.findById(id).orElseThrow();
        taller.setActivo(false);
        tallerEsRepository.save(taller);
    }

    @Override
    public void reactivarTallerEs(Integer id) {
        TallerEs taller = tallerEsRepository.findById(id).orElseThrow();
        taller.setActivo(true);
        tallerEsRepository.save(taller);
    }

    // ------- Inglés -------
    @Override
    public List<TallerEn> listarTalleresEn() {
        return tallerEnRepository.findAll();
    }

    @Override
    public void guardarTallerEn(TallerEn taller) {
        tallerEnRepository.save(taller);
    }

    @Override
    public void eliminarTallerEn(Integer id) {
        TallerEn taller = tallerEnRepository.findById(id).orElseThrow();
        taller.setActivo(false);
        tallerEnRepository.save(taller);
    }

    @Override
    public void reactivarTallerEn(Integer id) {
        TallerEn taller = tallerEnRepository.findById(id).orElseThrow();
        taller.setActivo(true);
        tallerEnRepository.save(taller);
    }

    @Override
    public TallerEs obtenerTallerEsPorId(Integer id) {
        return tallerEsRepository.findById(id).orElseThrow();
    }

    @Override
    public void eliminarFisicoTallerEs(Integer id) {
        tallerEsRepository.deleteById(id);
    }

    @Override
    public TallerEn obtenerTallerEnPorId(Integer id) {
        return tallerEnRepository.findById(id).orElseThrow();
    }

    @Override
    public void eliminarFisicoTallerEn(Integer id) {
        tallerEnRepository.deleteById(id);
    }
}
