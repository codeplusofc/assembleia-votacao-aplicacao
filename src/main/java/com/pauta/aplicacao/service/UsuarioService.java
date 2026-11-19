package com.pauta.aplicacao.service;

import com.pauta.aplicacao.model.Usuario;
import com.pauta.aplicacao.dto.UsuarioAtualizacaoRequest;
import com.pauta.aplicacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class UsuarioService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os dados do usuário são obrigatórios");
        }
        validarNome(usuario.getNome());
        usuario.setNome(usuario.getNome().trim());
        if (usuario.getCpf() != null) {
            usuario.setCpf(validarCpf(usuario.getCpf()));
        }
        if (usuario.getEmail() != null) {
            usuario.setEmail(validarEmail(usuario.getEmail()));
        }
        if (usuario.getIdade() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A idade deve ser maior que zero");
        }
        validarDadosUnicos(usuario, null);
        return salvarComTratamentoDeDuplicidade(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public void deletarUsuarioPorId(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    // Aplica somente os campos informados e mantém os demais dados do usuário.
    public Usuario atualizarUsuario(Long id, UsuarioAtualizacaoRequest atualizacao) {
        if (atualizacao == null || atualizacao.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe ao menos um campo para atualização");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (atualizacao.getNome() != null) {
            validarNome(atualizacao.getNome());
            usuario.setNome(atualizacao.getNome().trim());
        }
        if (atualizacao.getIdade() != null) {
            if (atualizacao.getIdade() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A idade deve ser maior que zero");
            }
            usuario.setIdade(atualizacao.getIdade());
        }
        if (atualizacao.getCpf() != null) {
            usuario.setCpf(validarCpf(atualizacao.getCpf()));
        }
        if (atualizacao.getEmail() != null) {
            usuario.setEmail(validarEmail(atualizacao.getEmail()));
        }
        if (atualizacao.getRua() != null) {
            if (atualizacao.getRua().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A rua não pode ficar em branco");
            }
            usuario.setRua(atualizacao.getRua().trim());
        }

        validarDadosUnicos(usuario, id);
        return salvarComTratamentoDeDuplicidade(usuario);
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome é obrigatório");
        }
    }

    // Valida o formato e os dígitos verificadores antes de salvar o CPF.
    private String validarCpf(String cpf) {
        String cpfNormalizado = cpf.replaceAll("\\D", "");
        if (cpfNormalizado.length() != 11 || cpfNormalizado.chars().distinct().count() == 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF informado é inválido");
        }

        int primeiroDigito = calcularDigitoVerificador(cpfNormalizado, 9);
        int segundoDigito = calcularDigitoVerificador(cpfNormalizado, 10);
        if (cpfNormalizado.charAt(9) - '0' != primeiroDigito
                || cpfNormalizado.charAt(10) - '0' != segundoDigito) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF informado é inválido");
        }

        return cpfNormalizado;
    }

    private int calcularDigitoVerificador(String cpf, int quantidadeDigitos) {
        int soma = 0;
        for (int indice = 0; indice < quantidadeDigitos; indice++) {
            soma += (cpf.charAt(indice) - '0') * (quantidadeDigitos + 1 - indice);
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    private String validarEmail(String email) {
        String emailNormalizado = email.trim().toLowerCase(Locale.ROOT);
        if (!EMAIL_PATTERN.matcher(emailNormalizado).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O e-mail informado é inválido");
        }
        return emailNormalizado;
    }

    // Exclui o próprio usuário das consultas para permitir manter os dados atuais.
    private void validarDadosUnicos(Usuario usuario, Long id) {
        boolean nomeDuplicado = id == null
                ? usuarioRepository.existsByNome(usuario.getNome())
                : usuarioRepository.existsByNomeAndIdNot(usuario.getNome(), id);
        if (nomeDuplicado) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com este nome");
        }

        if (usuario.getCpf() != null) {
            boolean cpfDuplicado = id == null
                    ? usuarioRepository.existsByCpf(usuario.getCpf())
                    : usuarioRepository.existsByCpfAndIdNot(usuario.getCpf(), id);
            if (cpfDuplicado) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com este CPF");
            }
        }

        if (usuario.getEmail() != null) {
            boolean emailDuplicado = id == null
                    ? usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())
                    : usuarioRepository.existsByEmailIgnoreCaseAndIdNot(usuario.getEmail(), id);
            if (emailDuplicado) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com este e-mail");
            }
        }
    }

    private Usuario salvarComTratamentoDeDuplicidade(Usuario usuario) {
        try {
            return usuarioRepository.saveAndFlush(usuario);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe um usuário com um dado único informado",
                    exception
            );
        }
    }
}
