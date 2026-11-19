package com.pauta.aplicacao.service;

import com.pauta.aplicacao.model.Pauta;
import com.pauta.aplicacao.repository.PautaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PautaService {

    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Optional<Pauta> buscarPorId(Long id) {
        return pautaRepository.findById(id);
    }

    public Pauta inserirPauta(Pauta pauta) {
        validarPauta(pauta);
        String nomeNormalizado = pauta.getNome().trim();
        if (pautaRepository.existsByNome(nomeNormalizado)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma pauta com este nome");
        }
        pauta.setNome(nomeNormalizado);
        return pautaRepository.save(pauta);
    }

    private void validarPauta(Pauta pauta) {
        if (pauta == null || pauta.getNome() == null || pauta.getNome().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da pauta deve ter entre 3 e 100 caracteres"
            );
        }

        int tamanhoNome = pauta.getNome().trim().length();
        if (tamanhoNome < 3 || tamanhoNome > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da pauta deve ter entre 3 e 100 caracteres"
            );
        }
    }

    public List<Pauta> buscarPautas() {
        return pautaRepository.findAll();
    }

    public void deletarPautaPorId(Long id) {
        if (!pautaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pauta não encontrada");
        }
        pautaRepository.deleteById(id);
    }
}
