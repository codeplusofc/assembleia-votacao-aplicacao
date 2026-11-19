package com.pauta.aplicacao.repository;

import com.pauta.aplicacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    boolean existsByUsuarioIdAndPautaId(Long UsuarioId, Long pautaId);

    List<Voto> findByPautaId(Long pautaId);
}