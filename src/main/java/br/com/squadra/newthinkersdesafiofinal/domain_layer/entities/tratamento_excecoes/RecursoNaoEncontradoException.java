package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes;

public final class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
