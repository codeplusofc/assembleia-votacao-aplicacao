package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.Voto;
import com.pauta.aplicacao.service.VotoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votos")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    public Voto registarVoto(@RequestBody Voto voto){
       return votoService.registrarVoto(voto);
    }

    @GetMapping("/pauta/{pautaId}")
    public List<Voto> buscarVotos(@PathVariable Long pautaId) {
        return votoService.buscarVotosPorPauta(pautaId);
    }
}