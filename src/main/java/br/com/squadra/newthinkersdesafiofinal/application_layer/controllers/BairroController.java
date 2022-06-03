package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.services.BairroService;
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

@RestController
@RequestMapping("/bairros")
@Tag(name = "BairroController")
public class BairroController {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Autowired
    private BairroService bairroService;

    // ---------- MÉTODOS DE CONTROLE ---------- //
    // ----- Cadastrar
    @Operation(summary = "Cadastrar", description = "Criar novo registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "201", description = "Created - Recurso criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(
            @Parameter(name = "bairroDtoEntrada", description = "Classe de transporte de dados de entrada.", required = true)
            @RequestBody @Valid BairroDtoEntrada bairroDtoEntrada) {
        return bairroService.cadastrar(bairroDtoEntrada);
    }

    // ----- Listar Todos
    @Operation(summary = "Listar", description = "Retornar objetos salvos no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @GetMapping
    public ResponseEntity<?> listar(BairroDtoEntrada filtros) {
        return bairroService.listar(filtros);
    }

    // ----- Consultar Por Id
    @Operation(summary = "Consultar", description = "Consultar banco de dados por chave identificadora.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> consultar(
            @Parameter(name = "codigoBairro", description = "Chave Identificadora", example = "10", required = true)
            @PathVariable(name = "id") Long codigoBairro) {
        return bairroService.consultar(codigoBairro);
    }

    // ----- Atualizar Por Id
    @Operation(summary = "Atualizar", description = "Alterar registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "204", description = "No Content - Tudo certo! Sem retorno."),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(
            @Parameter(name = "codigoBairro", description = "Chave Identificadora", example = "8", required = true)
            @PathVariable(name = "id") Long codigoBairro,
            @Parameter(name = "bairroDtoEntrada", description = "Classe de transporte de dados de entrada.", required = true)
            @RequestBody @Valid BairroDtoEntrada bairroDtoEntrada) {
        return bairroService.atualizar(codigoBairro, bairroDtoEntrada);
    }

    // ----- Deletar Por Id
    @Operation(summary = "Deletar", description = "Deletar registro no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "404", description = "Not Found - Recurso não encontrado!")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletar(
            @Parameter(name = "codigoBairro", description = "Chave Identificadora", example = "7", required = true)
            @PathVariable(name = "id") Long codigoBairro) {
        return bairroService.deletar(codigoBairro);
    }
}
