package com.pauta.aplicacao.model;

public class ResultadoVotacao {

    public Long pautaId;
    public long votosSim;
    public long votosNao;
    public long totalVotos;

    public ResultadoVotacao(Long pautaId, long votosSim, long votosNao) {
        this.pautaId = pautaId;
        this.votosSim = votosSim;
        this.votosNao = votosNao;
        this.totalVotos = votosSim + votosNao;
    }
}