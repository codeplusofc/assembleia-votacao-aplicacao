package com.pauta.aplicacao.repository;

import com.pauta.aplicacao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario,Long> {

    // Consulta se ja existe um usuario cadastrado com o nome informado.
    boolean existsByNome(String nome);
}
