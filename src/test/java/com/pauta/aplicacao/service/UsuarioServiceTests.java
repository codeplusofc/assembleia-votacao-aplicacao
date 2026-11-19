package com.pauta.aplicacao.service;

import com.pauta.aplicacao.dto.UsuarioAtualizacaoRequest;
import com.pauta.aplicacao.model.Usuario;
import com.pauta.aplicacao.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void atualizaSomenteCamposInformadosEIgnoraOsDadosUnicosDoProprioUsuario() {
        Usuario existente = usuario();
        UsuarioAtualizacaoRequest atualizacao = new UsuarioAtualizacaoRequest();
        atualizacao.setEmail("  NOVO@EXAMPLE.COM ");
        atualizacao.setRua(" Avenida Central ");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.saveAndFlush(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.atualizarUsuario(1L, atualizacao);

        assertEquals("novo@example.com", resultado.getEmail());
        assertEquals("Avenida Central", resultado.getRua());
        assertEquals("Usuario Original", resultado.getNome());
        verify(usuarioRepository).existsByNomeAndIdNot("Usuario Original", 1L);
        verify(usuarioRepository).existsByCpfAndIdNot("12345678901", 1L);
        verify(usuarioRepository).existsByEmailIgnoreCaseAndIdNot("novo@example.com", 1L);
    }

    @Test
    void rejeitaEmailDuplicadoComConflito() {
        UsuarioAtualizacaoRequest atualizacao = new UsuarioAtualizacaoRequest();
        atualizacao.setEmail("duplicado@example.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario()));
        when(usuarioRepository.existsByEmailIgnoreCaseAndIdNot("duplicado@example.com", 1L)).thenReturn(true);

        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> usuarioService.atualizarUsuario(1L, atualizacao)
        );

        assertEquals(HttpStatus.CONFLICT, erro.getStatusCode());
        verify(usuarioRepository, never()).saveAndFlush(any(Usuario.class));
    }

    @Test
    void rejeitaCpfInvalidoComBadRequest() {
        UsuarioAtualizacaoRequest atualizacao = new UsuarioAtualizacaoRequest();
        atualizacao.setCpf("123");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario()));

        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> usuarioService.atualizarUsuario(1L, atualizacao)
        );

        assertEquals(HttpStatus.BAD_REQUEST, erro.getStatusCode());
        verify(usuarioRepository, never()).saveAndFlush(any(Usuario.class));
    }

    @Test
    void retornaNotFoundQuandoUsuarioNaoExiste() {
        UsuarioAtualizacaoRequest atualizacao = new UsuarioAtualizacaoRequest();
        atualizacao.setRua("Rua Nova");
        when(usuarioRepository.findById(42L)).thenReturn(Optional.empty());

        ResponseStatusException erro = assertThrows(
                ResponseStatusException.class,
                () -> usuarioService.atualizarUsuario(42L, atualizacao)
        );

        assertEquals(HttpStatus.NOT_FOUND, erro.getStatusCode());
    }

    private Usuario usuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Usuario Original");
        usuario.setIdade(30);
        usuario.setCpf("12345678901");
        usuario.setEmail("original@example.com");
        usuario.setRua("Rua Original");
        return usuario;
    }
}
