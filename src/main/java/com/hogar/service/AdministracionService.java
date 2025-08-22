package com.hogar.service;

import com.hogar.domain.ReservaTaller;
import com.hogar.domain.Usuario;
import com.hogar.domain.TallerEn;
import com.hogar.domain.TallerEs;
import com.hogar.repository.UsuarioRepository;
import com.hogar.repository.ReservaTallerRepository;
import com.hogar.repository.TallerEnRepository;
import com.hogar.repository.TallerEsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministracionService {

    private final UsuarioRepository usuarioRepository;
    private final TallerEsRepository tallerEsRepository;
    private final TallerEnRepository tallerEnRepository;
    private final ReservaTallerRepository reservaTallerRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<TallerEs> listarTalleresEs() {
        return tallerEsRepository.findAll();
    }

    public List<TallerEn> listarTalleresEn() {
        return tallerEnRepository.findAll();

    }

    public Optional<Usuario> encontrarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public void actualizarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            usuario.setPassword(usuarioExistente.getPassword());
        }
        usuarioRepository.save(usuario);
    }

    public List<ReservaTaller> listarReservas() {
        return reservaTallerRepository.findAll();
    }
}
