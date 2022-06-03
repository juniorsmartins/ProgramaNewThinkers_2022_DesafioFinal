package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes;

public class RegrasDeNegocioVioladasException extends RuntimeException {

    public RegrasDeNegocioVioladasException(String mensagem) {
        super(mensagem);
    }
}
