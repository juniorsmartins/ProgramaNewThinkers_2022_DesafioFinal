package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.regras_negocio;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;

public interface IRegrasUfCadastrar {

    void validar(UfDtoEntrada ufDtoEntrada);
}
