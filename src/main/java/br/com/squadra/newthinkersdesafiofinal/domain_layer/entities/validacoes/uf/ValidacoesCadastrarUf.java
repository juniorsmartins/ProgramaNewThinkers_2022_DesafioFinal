package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.uf;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;

public interface ValidacoesCadastrarUf {

    void validar(UfDtoEntrada ufDtoEntrada);
}
