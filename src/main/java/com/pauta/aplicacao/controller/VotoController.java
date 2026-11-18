package com.pauta.aplicacao.controller;


import com.pauta.aplicacao.model.ResultadoVotacao;
import com.pauta.aplicacao.model.Voto;
import com.pauta.aplicacao.service.VotoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Votos")
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
    public java.util.List<Voto> buscarVotos(@PathVariable Long pautaId) {
        return votoService.buscarVotosPorPauta(pautaId);
    }

    @GetMapping("/pauta/{pautaId}/resultado")
    public ResultadoVotacao resultado(@PathVariable Long pautaId) {
        return votoService.calcularResultado(pautaId);
    }



}
