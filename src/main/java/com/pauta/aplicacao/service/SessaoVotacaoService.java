package com.pauta.aplicacao.service;

import com.pauta.aplicacao.model.Pauta;
import com.pauta.aplicacao.model.SessaoVotacao;
import com.pauta.aplicacao.repository.PautaRepository;
import com.pauta.aplicacao.repository.SessaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class SessaoVotacaoService {

    private final SessaoRepository sessaoVotacaoRepository;
    private final PautaRepository pautaRepository;

    public SessaoVotacaoService(
            SessaoRepository sessaoVotacaoRepository,
            PautaRepository pautaRepository) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.pautaRepository = pautaRepository;
    }

    public SessaoVotacao abrirSessao(Long pautaId, Long duracaoMinutos) {

        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pauta não encontrada"
                ));

        if (duracaoMinutos == null) {
            duracaoMinutos = 1L;
        }

        SessaoVotacao sessao = new SessaoVotacao();

        sessao.pauta = pauta;
        sessao.inicio = LocalDateTime.now();
        sessao.fim = sessao.inicio.plusMinutes(duracaoMinutos);

        return sessaoVotacaoRepository.save(sessao);
    }
}