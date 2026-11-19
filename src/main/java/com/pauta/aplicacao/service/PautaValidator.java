package com.pauta.aplicacao.service;

    import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

    @Component
    public class PautaValidator {

        public String validarNome(String nome) {

            if (nome == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O nome da pauta deve ter entre 3 e 100 caracteres"
                );
            }

            String nomeNormalizado = nome.trim();

            if (nomeNormalizado.length() < 3 ||
                    nomeNormalizado.length() > 100) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O nome da pauta deve ter entre 3 e 100 caracteres"
                );
            }

            return nomeNormalizado;
        }
    }

