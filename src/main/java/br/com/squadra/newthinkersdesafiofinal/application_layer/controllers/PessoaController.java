package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntradaListar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.PessoaDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.detalhado.PessoaDtoSaidaDetalhado;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.portas.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
@Tag(name = "PessoaController")
public class PessoaController {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Autowired
    private PessoaService pessoaService;

    // ---------- MÉTODOS DE CONTROLE ---------- //
    // ----- Cadastrar
    @Operation(summary = "Cadastrar", description = "Criar novo registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "409", description = "Conflict - Informação em conflito no servidor.")
    })
    @PostMapping
    @Transactional
    public List<PessoaDtoSaida> cadastrar(
            @Parameter(name = "pessoaDtoEntrada", description = "Classe de transporte de dados de entrada.", required = true)
            @RequestBody @Valid PessoaDtoEntrada pessoaDtoEntrada) {
        return pessoaService.cadastrar(pessoaDtoEntrada);
    }

    // ----- Listar Todos
    @Operation(summary = "Listar", description = "Retornar objetos salvos no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @GetMapping
    public ResponseEntity<?> listar(PessoaDtoEntradaListar pessoaDtoEntradaListar) {
        return pessoaService.listar(pessoaDtoEntradaListar);
    }

    // ----- Consultar Por Id
    @Operation(summary = "Consultar", description = "Consultar banco de dados por chave identificadora.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @GetMapping("/{id}")
    public PessoaDtoSaidaDetalhado consultar(
            @Parameter(name = "codigoPessoa", description = "Chave Identificadora", example = "10", required = true)
            @PathVariable(name = "id") Long codigoPessoa) {
        return pessoaService.consultar(codigoPessoa);
    }

    // ----- Atualizar Por Id
    @Operation(summary = "Atualizar", description = "Alterar registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!"),
            @ApiResponse(responseCode = "409", description = "Conflict - Informação em conflito no servidor.")
    })
    @PutMapping
    @Transactional
    public List<PessoaDtoSaida> atualizar(
            @Parameter(name = "pessoaDtoEntrada", description = "Classe de transporte de dados de entrada.", required = true)
            @RequestBody @Valid PessoaDtoEntradaAtualizar pessoaDtoEntradaAtualizar) {
        return pessoaService.atualizar(pessoaDtoEntradaAtualizar);
    }

    // ----- Deletar Por Id
    @Operation(summary = "Deletar", description = "Deletar registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "204", description = "No Content - Tudo certo! Sem retorno."),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public List<PessoaDtoSaida> deletar(
            @Parameter(name = "codigoPessoa", description = "Chave Identificadora", example = "7", required = true)
            @PathVariable(name = "id") Long codigoPessoa) {
        return pessoaService.deletar(codigoPessoa);
    }
}
