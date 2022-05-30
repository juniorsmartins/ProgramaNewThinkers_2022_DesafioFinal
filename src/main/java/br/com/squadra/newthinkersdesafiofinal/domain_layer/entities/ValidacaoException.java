package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities;

public final class ValidacaoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}
