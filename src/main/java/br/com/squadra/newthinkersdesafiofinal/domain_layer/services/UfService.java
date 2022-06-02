package br.com.squadra.newthinkersdesafiofinal.domain_layer.services;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.UfDtoSaida;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface UfService {

    List<UfDtoSaida> cadastrar(UfDtoEntrada ufDtoEntrada);
    ResponseEntity<?> listar(UfDtoEntrada filtros);
    UfDtoSaida consultar(Long codigoUF);
    List<UfDtoSaida> atualizar(UfDtoEntrada ufDtoEntrada);
    List<UfDtoSaida> deletar(Long codigoUF);
}
