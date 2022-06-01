package br.com.squadra.newthinkersdesafiofinal.application_layer.configs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Chave Identificadora não encontrada!")
public class PersonalizarRetornoDeNotFound extends Exception {
    private static final long serialVersionUID = 1L;

    public PersonalizarRetornoDeNotFound(String mensagemDeErro) {
        super(mensagemDeErro);
    }
}
