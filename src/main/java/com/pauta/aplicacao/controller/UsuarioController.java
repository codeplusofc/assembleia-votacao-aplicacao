package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Usuario;
import com.pauta.aplicacao.dto.UsuarioAtualizacaoRequest;
import com.pauta.aplicacao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarUsuarios() {
     return usuarioService.listarUsuarios();
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.criarUsuario(usuario);

    }

    // Atualiza parcialmente um usuário; o id é recebido pela URL.
    @PatchMapping("/{id}")
    public Usuario atualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioAtualizacaoRequest atualizacao
    ) {
        return usuarioService.atualizarUsuario(id, atualizacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPorId(@PathVariable Long id) {
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }
}