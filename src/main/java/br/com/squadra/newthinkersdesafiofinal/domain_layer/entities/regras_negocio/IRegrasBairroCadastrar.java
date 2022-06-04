package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;

public interface IRegrasBairroCadastrar {

    void validar(BairroDtoEntrada bairroDtoEntrada);
}
