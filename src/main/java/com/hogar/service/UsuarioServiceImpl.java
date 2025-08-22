package com.hogar.service;

import com.hogar.domain.Rol;
import com.hogar.domain.Usuario;
import com.hogar.repository.RolRepository;
import com.hogar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        Rol rolUsuario = rolRepository.findByNombre("USUARIO");
        usuario.setRol(rolUsuario);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorCedula(String cedula) {
        return usuarioRepository.findByCedula(cedula);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public void marcarReserva(Integer idUsuario) {
        usuarioRepository.marcarReserva(idUsuario);
    }
}
