package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Usuario;
import com.pauta.aplicacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        // validacao que impede o cadastro de  usuario com o mesmo nome.
        if (usuarioRepository.existsByNome(usuario.getNome())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um usuário com este nome"
            );
        }
        return usuarioRepository.save(usuario);
    }
    @DeleteMapping("/deletar")
    public String deletartudo(){
    usuarioRepository.deleteAll();

     return "Usuarios deletados";

    }

}

//Creat = Criar
//Read = Ler
//Update = Atualizar
//Delete = Deletar