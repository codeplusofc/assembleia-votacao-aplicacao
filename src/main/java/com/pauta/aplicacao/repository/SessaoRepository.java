package com.pauta.aplicacao.repository;

import com.pauta.aplicacao.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<SessaoVotacao, Long> {

    Optional<SessaoVotacao> findFirstByPautaIdOrderByInicioDesc(Long pautaId);

}