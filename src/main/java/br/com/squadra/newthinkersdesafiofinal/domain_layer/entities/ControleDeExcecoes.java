package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControleDeExcecoes {

    @ExceptionHandler(RegraDeNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerRegraDeNegocioException(RegraDeNegocioException regraDeNegocioException) {

        String mensagemDeErro = regraDeNegocioException.getMessage();

        return new ApiErrors(mensagemDeErro);
    }
}
