package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Pauta;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    @GetMapping
    public Pauta retornarPauta(){
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDescricao("valorstring");
        return pauta;
    }
}
