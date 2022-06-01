package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.validacoes.bairro;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;

public interface ValidacoesCadastrarBairro {

    void validar(BairroDtoEntrada bairroDtoEntrada);
}
