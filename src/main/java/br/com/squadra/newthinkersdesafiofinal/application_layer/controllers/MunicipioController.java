package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntradaAtualizar;
import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.MunicipioDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.portas.MunicipioService;
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
@RequestMapping("/municipio")
@Tag(name = "MunicipioController")
public class MunicipioController {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Autowired
    private MunicipioService municipioService;

    // ---------- MÉTODOS DE CONTROLE ---------- //
    // ----- Cadastrar
    @Operation(summary = "Cadastrar", description = "Criar novo registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!"),
            @ApiResponse(responseCode = "409", description = "Conflict - Informação em conflito no servidor.")
    })
    @PostMapping
    @Transactional
    public List<MunicipioDtoSaida> cadastrar(
            @Parameter(name = "municipioDtoEntrada", description = "Classe de transporte de dados de entrada.", required = true)
            @RequestBody @Valid MunicipioDtoEntrada municipioDtoEntrada) {
        return municipioService.cadastrar(municipioDtoEntrada);
    }

    // ----- Listar Todos
    @Operation(summary = "Listar", description = "Retornar objetos salvos no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @GetMapping
    public ResponseEntity<?> listar(MunicipioDtoEntrada filtros) {
        return municipioService.listar(filtros);
    }

    // ----- Consultar Por Id
    @Operation(summary = "Consultar", description = "Consultar banco de dados por chave identificadora.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @GetMapping("/{id}")
    public MunicipioDtoSaida consultar(
            @Parameter(name = "codigoMunicipio", description = "Chave Identificadora", example = "10", required = true)
            @PathVariable(name = "id") Long codigoMunicipio) {
        return municipioService.consultar(codigoMunicipio);
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
    public List<MunicipioDtoSaida> atualizar(
            @Parameter(name = "municipioDtoEntrada", description = "Classe de transporte de dados de entrada.", required = true)
            @RequestBody @Valid MunicipioDtoEntradaAtualizar municipioDtoEntradaAtualizar) {
        return municipioService.atualizar(municipioDtoEntradaAtualizar);
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
    public List<MunicipioDtoSaida> deletar(
            @Parameter(name = "codigoMunicipio", description = "Chave Identificadora", example = "7", required = true)
            @PathVariable(name = "id") Long codigoMunicipio) {
        return municipioService.deletar(codigoMunicipio);
    }
}
