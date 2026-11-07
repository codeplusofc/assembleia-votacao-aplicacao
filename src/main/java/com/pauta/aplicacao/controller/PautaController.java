package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Pauta;
import com.pauta.aplicacao.service.PautaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPautaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.buscarPorId(id));
    }
    @GetMapping
    public ResponseEntity<List<Pauta>> listarPautas() {
        List<Pauta> pautas = pautaService.buscarPautas();
        return ResponseEntity.ok(pautas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPorId(@PathVariable Long id) {
        Pauta pauta = pautaService.buscarPorId(id);
        return ResponseEntity.ok(pauta);
    }
}