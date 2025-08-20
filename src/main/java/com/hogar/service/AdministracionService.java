package com.hogar.service;

import com.hogar.domain.ReservaTaller;
import com.hogar.domain.Usuario;
import com.hogar.domain.Taller;
import com.hogar.repository.UsuarioRepository;
import com.hogar.repository.TallerRepository;
import com.hogar.repository.ReservaTallerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministracionService {

    private final UsuarioRepository usuarioRepository;
    private final TallerRepository tallerRepository;
    private final ReservaTallerRepository reservaTallerRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Taller> listarTalleres() {
        return tallerRepository.findAll();
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