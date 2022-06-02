package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.UfDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.services.UfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ufs")
@Tag(name = "UfController")
public class UfController {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Autowired
    private UfService ufService;

    // ---------- MÉTODOS DE CONTROLE ---------- //
    // ----- Cadastrar
    @Operation(summary = "Cadastrar", description = "Criar novo registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "201", description = "Created - Recurso criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro interno do Servidor!")
    })
    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public List<UfDtoSaida> cadastrar(
            @Parameter(name = "ufDtoEntrada", description = "Classe de transporte de dados de entrada.", required = true)
            @RequestBody @Valid UfDtoEntrada ufDtoEntrada) {
        return ufService.cadastrar(ufDtoEntrada);
    }

    // ----- Listar Todos
    @Operation(summary = "Listar", description = "Retornar objetos salvos no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @GetMapping
    public ResponseEntity<?> listar(UfDtoEntrada filtros) {
        return ufService.listar(filtros);
    }

    // ----- Consultar Por Id
    @Operation(summary = "Consultar", description = "Consultar banco de dados por chave identificadora.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @GetMapping("/{id}")
    public UfDtoSaida consultar(
            @Parameter(name = "codigoUF", description = "Chave Identificadora", example = "10", required = true)
            @PathVariable(name = "id") Long codigoUF) {
        return ufService.consultar(codigoUF);
    }

    // ----- Atualizar Por Id
    @Operation(summary = "Atualizar", description = "Alterar registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "204", description = "No Content - Tudo certo! Sem retorno."),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @PutMapping
    @Transactional
    /*@ResponseStatus(HttpStatus.NO_CONTENT)*/
    public List<UfDtoSaida> atualizar(
            @Parameter(name = "ufDtoEntrada", description = "Classe de transporte de dados de entrada.", required = true)
            @RequestBody @Valid UfDtoEntrada ufDtoEntrada) {
        return ufService.atualizar(ufDtoEntrada);
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
    /*@ResponseStatus(HttpStatus.NO_CONTENT)*/
    public List<UfDtoSaida> deletar(
            @Parameter(name = "codigoUf", description = "Chave Identificadora", example = "7", required = true)
            @PathVariable(name = "id") Long codigoUf) {
        return ufService.deletar(codigoUf);
    }
}
