package com.pauta.aplicacao.service;

import com.pauta.aplicacao.model.ResultadoVotacao;
import com.pauta.aplicacao.model.Voto;
import com.pauta.aplicacao.repository.VotoRepository;
import com.pauta.aplicacao.repository.UsuarioRepository;
import com.pauta.aplicacao.repository.PautaRepository;
import com.pauta.aplicacao.repository.SessaoRepository;
import com.pauta.aplicacao.model.SessaoVotacao;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class VotoService {

    private final VotoRepository votoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PautaRepository pautaRepository;
    private final SessaoRepository sessaoRepository;

    public VotoService(
            VotoRepository votoRepository,
            UsuarioRepository usuarioRepository,
            PautaRepository pautaRepository,
            SessaoRepository sessaoRepository) {

        this.votoRepository = votoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pautaRepository = pautaRepository;
        this.sessaoRepository = sessaoRepository;
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

        SessaoVotacao sessao = sessaoRepository
                .findFirstByPautaIdOrderByInicioDesc(pauta.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe sessão para essa pauta"));

        LocalDateTime agora = LocalDateTime.now();

        if (agora.isBefore(sessao.inicio) || !agora.isBefore(sessao.fim)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A sessão de votação está encerrada");
        }

        voto.usuario = usuario;
        voto.pauta = pauta;

        return votoRepository.save(voto);
    }
    public java.util.List<Voto> buscarVotosPorPauta(Long pautaId) {

        if (!pautaRepository.existsById(pautaId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pauta não encontrada");
        }

        return votoRepository.findByPautaId(pautaId);
    }

    public ResultadoVotacao calcularResultado(Long pautaId) {

        java.util.List<Voto> votos = buscarVotosPorPauta(pautaId);

        long sim = 0;
        long nao = 0;

        for (Voto voto : votos) {
            if (voto.voto) {
                sim++;
            } else {
                nao++;
            }
        }

        return new ResultadoVotacao(pautaId, sim, nao);
    }
}