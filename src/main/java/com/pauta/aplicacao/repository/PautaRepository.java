package com.pauta.aplicacao.repository;

import com.pauta.aplicacao.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface PautaRepository extends JpaRepository<Pauta,Long> {


    public boolean existsByNome(String nome);
}
