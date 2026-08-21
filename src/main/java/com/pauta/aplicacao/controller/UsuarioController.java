package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Usuario;
import com.pauta.aplicacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping
    public List<Usuario> retornaUsuario() {



        return usuarioRepository.findAll();
    }
    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario){

        return usuarioRepository.save(usuario);

    }
}
//Creat = Criar
//Read = Ler
//Update = Atualizar
//Delete = Deletar