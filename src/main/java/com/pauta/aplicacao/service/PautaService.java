package com.pauta.aplicacao.service;

import com.pauta.aplicacao.model.Pauta;
import com.pauta.aplicacao.model.Usuario;
import com.pauta.aplicacao.repository.PautaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
public class PautaService {

    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta pauta  = new Pauta();

    public Pauta buscarPorId(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pauta com ID " + id + " não encontrada."
                ));
    }



    public Pauta validacaopauta(Pauta pauta){
        pauta.nomePauta = pauta.nomePauta.trim();

        if (pauta.nomePauta == null || pauta.nomePauta.isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da pauta deve ter entre 3 e 100 caracteres");
        }
        if (pauta.nomePauta.length() < 3 || pauta.nomePauta.length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da pauta deve ter entre 3 e 100 caracteres");
        }
        if (pautaRepository.existsByNome((pauta.nomePauta))) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe uma Pauta com este nome"
            );
        }
        return pautaRepository.save(pauta);
    }

    }



