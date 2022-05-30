package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.uf;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;

public interface ValidacoesUf {

    void validar(UfDtoEntrada ufDtoEntrada);
}
