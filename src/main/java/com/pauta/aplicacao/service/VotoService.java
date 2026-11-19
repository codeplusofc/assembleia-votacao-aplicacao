package com.pauta.aplicacao.service;

import com.pauta.aplicacao.model.Voto;
import com.pauta.aplicacao.repository.VotoRepository;
import com.pauta.aplicacao.repository.UsuarioRepository;
import com.pauta.aplicacao.repository.PautaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VotoService {

    private final VotoRepository votoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PautaRepository pautaRepository;

    public VotoService(VotoRepository votoRepository, UsuarioRepository usuarioRepository, PautaRepository pautaRepository) {
        this.votoRepository = votoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pautaRepository = pautaRepository;
    }

    public Voto registrarVoto(Voto voto) {
        if (voto == null || voto.usuario == null || voto.pauta == null
                || voto.usuario.getId() == null || voto.pauta.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do voto inválidos");
        }

        var usuario = usuarioRepository.findById(voto.usuario.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        var pauta = pautaRepository.findById(voto.pauta.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pauta não encontrada"));

        if (votoRepository.existsByUsuarioIdAndPautaId(usuario.getId(), pauta.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esse usuário já votou nessa pauta");
        }
        return votoRepository.save(voto);
    }

    public List<Voto> buscarVotosPorPauta(Long pautaId) {
        if (!pautaRepository.existsById(pautaId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pauta não encontrada");
        }
        return votoRepository.findByPautaId(pautaId);
    }
}