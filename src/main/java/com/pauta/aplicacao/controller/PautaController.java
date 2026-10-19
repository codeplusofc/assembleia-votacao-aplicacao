package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Pauta;
import com.pauta.aplicacao.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pautas")
public class PautaController {
    @Autowired
    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPautaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.buscarPorId(id));
    }

    @PostMapping
    public Pauta criarPauta(@RequestBody Pauta pauta) {
        return pautaService.validacaopauta(pauta);
    }




}
