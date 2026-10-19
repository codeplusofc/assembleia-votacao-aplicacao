package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Usuario;
import com.pauta.aplicacao.repository.UsuarioRepository;
import com.pauta.aplicacao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarUsuarios() {
     return usuarioService.listarUsuarios();
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.criarUsuario(usuario);

    }

    @DeleteMapping("/{id}")
    public void deletarUsuarioPorId(@PathVariable Long id) {
        usuarioService.deletarUsuarioPorId(id);
    }
}