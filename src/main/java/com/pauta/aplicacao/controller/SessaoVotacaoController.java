package com.pauta.aplicacao.controller;

import com.pauta.aplicacao.model.SessaoVotacao;
import com.pauta.aplicacao.service.SessaoVotacaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessoes")
public class SessaoVotacaoController {

    private final SessaoVotacaoService sessaoVotacaoService;

    public SessaoVotacaoController(SessaoVotacaoService sessaoVotacaoService) {
        this.sessaoVotacaoService = sessaoVotacaoService;
    }

    @PostMapping("/{pautaId}")
    public SessaoVotacao abrirSessao(
            @PathVariable Long pautaId,
            @RequestParam(required = false) Long duracaoMinutos) {

        return sessaoVotacaoService.abrirSessao(pautaId, duracaoMinutos);
    }
}