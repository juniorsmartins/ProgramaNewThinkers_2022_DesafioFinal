package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.services.UfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;

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
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autorizado!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Usuário não autenticado!"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro interno do servidor!")
    })
    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Parameter(name = "ufDtoEntrada", description = "Classe de transporte de dados de entrada.", required = true)
            @RequestBody @Valid UfDtoEntrada ufDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        return ufService.cadastrar(ufDtoEntrada, uriComponentsBuilder);
    }

    // ----- Listar Todos
    @Operation(summary = "Listar", description = "Retornar objetos salvos no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autorizado!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Usuário não autenticado!"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro interno do servidor!")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        return ufService.listar();
    }

    // ----- Consultar Por Id
    @Operation(summary = "Consultar", description = "Consultar banco de dados por chave identificadora.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autorizado!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Usuário não autenticado!"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro interno do servidor!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> consultar(
            @Parameter(name = "codigoUf", description = "Chave Identificadora", example = "10", required = true)
            @PathVariable(name = "id") Long codigoUf) {
        return ufService.consultar(codigoUf);
    }

    // ----- Atualizar Por Id
    @Operation(summary = "Atualizar", description = "Alterar registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autorizado!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Usuário não autenticado!"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro interno do servidor!")
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(
            @Parameter(name = "codigoUf", description = "Chave Identificadora", example = "8", required = true)
            @PathVariable(name = "id") Long codigoUf,
            @Parameter(name = "ufDtoEntrada", description = "Classe de transporte de dados de entrada.", required = true)
            @RequestBody @Valid UfDtoEntrada ufDtoEntrada) {
        return ufService.atualizar(codigoUf, ufDtoEntrada);
    }

    // ----- Deletar Por Id
    @Operation(summary = "Deletar", description = "Deletar registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autorizado!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Usuário não autenticado!"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro interno do servidor!")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(name = "codigoUf", description = "Chave Identificadora", example = "7", required = true)
            @PathVariable(name = "id") Long codigoUf) {
        return ufService.deletar(codigoUf);
    }
}
