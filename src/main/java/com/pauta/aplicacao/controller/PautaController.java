package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Pauta;
import com.pauta.aplicacao.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    @Autowired
    private PautaRepository pautaRepository;

    @PostMapping
    public Pauta criarPauta(@RequestBody Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    @GetMapping
    public List<Pauta> listarPautas() {
        return pautaRepository.findAll();
    }
}
