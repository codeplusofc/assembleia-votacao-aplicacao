package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Pauta;
import com.pauta.aplicacao.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @GetMapping
    public List<Pauta> buscarPautas(){
        return pautaService.buscarPautas();
    }

    @GetMapping("/{id}")
    public Optional<Pauta> buscarPautaPorId(@PathVariable Long id) {
        return pautaService.buscarPorId(id);
    }

    @PostMapping
    public Pauta criarPauta(@RequestBody Pauta pauta) {
        return pautaService.inserirPauta(pauta);
    }
}
