package com.hogar.service;

import com.hogar.domain.Taller;
import com.hogar.repository.TallerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TallerServiceImpl implements TallerService {

    @Autowired
    private TallerRepository tallerRepository;

    @Override
    public List<Taller> listarTalleres() {
        return tallerRepository.findByActivoTrue();
    }

    @Override
    public Taller guardarTaller(Taller taller) {
        return tallerRepository.save(taller);
    }

    @Override
    public void eliminarTaller(Integer id) {
        Optional<Taller> opt = tallerRepository.findById(id);
        if (opt.isPresent()) {
            Taller taller = opt.get();
            taller.setActivo(false);
            tallerRepository.save(taller);
        }
    }

    @Override
    public void reactivarTaller(Integer id) {
        Optional<Taller> opt = tallerRepository.findById(id);
        if (opt.isPresent()) {
            Taller taller = opt.get();
            taller.setActivo(true);
            tallerRepository.save(taller);
        }
    }

    @Override
    public Optional<Taller> buscarPorId(Integer id) {
        return tallerRepository.findById(id);
    }
}