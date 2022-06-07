package br.com.squadra.newthinkersdesafiofinal.domain_layer.portas;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntradaListar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.MunicipioDtoSaida;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface MunicipioService {

    List<MunicipioDtoSaida> cadastrar(MunicipioDtoEntrada municipioDtoEntrada);
    ResponseEntity<?> listar(MunicipioDtoEntradaListar municipioDtoEntradaListar);
    MunicipioDtoSaida consultar(Long codigoMunicipio);
    List<MunicipioDtoSaida> atualizar(MunicipioDtoEntradaAtualizar municipioDtoEntradaAtualizar);
    List<MunicipioDtoSaida> deletar(Long codigoMunicipio);
}
