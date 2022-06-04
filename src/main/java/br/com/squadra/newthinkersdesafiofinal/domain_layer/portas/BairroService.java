package br.com.squadra.newthinkersdesafiofinal.domain_layer.portas;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.BairroDtoSaida;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface BairroService {

    List<BairroDtoSaida> cadastrar(BairroDtoEntrada bairroDtoEntrada);
    ResponseEntity<?> listar(BairroDtoEntrada bairroDtoEntrada);
    BairroDtoSaida consultar(Long codigoBairro);
    List<BairroDtoSaida> atualizar(BairroDtoEntradaAtualizar bairroDtoEntradaAtualizar);
    List<BairroDtoSaida> deletar(Long codigoBairro);
}
