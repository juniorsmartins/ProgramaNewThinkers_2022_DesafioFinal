package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.pessoa;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;

public interface ValidacoesCadastrarPessoa {
    void validar(PessoaDtoEntrada pessoaDtoEntrada);
}
