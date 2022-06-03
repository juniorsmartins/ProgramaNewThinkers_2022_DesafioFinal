package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;

public interface IRegrasMunicipioCadastrar {

    void validar(MunicipioDtoEntrada municipioDtoEntrada);
}
