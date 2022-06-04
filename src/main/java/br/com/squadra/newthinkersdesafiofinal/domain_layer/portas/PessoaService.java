package br.com.squadra.newthinkersdesafiofinal.domain_layer.portas;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.PessoaDtoSaida;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface PessoaService {

    List<PessoaDtoSaida> cadastrar(PessoaDtoEntrada pessoaDtoEntrada);
    ResponseEntity<?> listar(PessoaDtoEntrada pessoaDtoEntrada);
    PessoaDtoSaida consultar(Long codigoPessoa);
    List<PessoaDtoSaida> atualizar(PessoaDtoEntradaAtualizar pessoaDtoEntradaAtualizar);
    List<PessoaDtoSaida> deletar(Long codigoPessoa);
}
