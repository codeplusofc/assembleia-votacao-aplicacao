package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Usuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @GetMapping
    public Usuario retornaUsuario() {

        Usuario usuario = new Usuario();
        usuario.setNome("Joao");
        usuario.setCpf("12345");
        usuario.setIdade(18);
        usuario.setRua("Bartolomeu seila");

        return usuario;
    }
}
