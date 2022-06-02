package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities;

public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
