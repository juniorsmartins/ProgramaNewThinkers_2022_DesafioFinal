package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities;

public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
