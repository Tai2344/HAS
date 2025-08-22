package com.hogar.service;

import com.hogar.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorCedula(String cedula);
    List<Usuario> listarUsuarios();
    Optional<Usuario> buscarPorId(Integer id);
    void marcarReserva(Integer idUsuario);
}