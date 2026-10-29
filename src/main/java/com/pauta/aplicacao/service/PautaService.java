package com.pauta.aplicacao.service;

import com.pauta.aplicacao.model.Pauta;
import com.pauta.aplicacao.model.Usuario;
import com.pauta.aplicacao.repository.PautaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PautaService {

    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta pauta = new Pauta();

    public Pauta buscarPorId(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pauta com ID " + id + " não encontrada."
                ));
    }

    public Pauta inserirPauta(Pauta pauta) {
        validacaoPauta(pauta);
        return pautaRepository.save(pauta);
    }

    private void validacaoPauta(Pauta pauta) {
        pauta.getNome().trim();

        if (pauta.getNome() == null || pauta.getNome().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da pauta deve ter entre 3 e 100 caracteres");
        }
        if (pauta.getNome().length() < 3 || pauta.getNome().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da pauta deve ter entre 3 e 100 caracteres");
        }
        if (pautaRepository.existsByNome((pauta.getNome()))) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe uma Pauta com este nome"
            );
        }

    }

    public List<Pauta> buscarPautas() {
        return pautaRepository.findAll();
    }
}



