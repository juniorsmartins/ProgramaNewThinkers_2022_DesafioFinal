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
import org.springframework.web.util.UriComponentsBuilder;
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
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autorizado!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Usuário não autenticado!"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro interno do servidor!")
    })
    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Parameter(name = "BairroDtoEntrada", description = "Objeto de transporte para ficha de cadastro de Bairro.")
            @RequestBody @Valid BairroDtoEntrada bairroDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        return bairroService.cadastrar(bairroDtoEntrada, uriComponentsBuilder);
    }

    // ----- Listar Todos
    @Operation(summary = "Listar", description = "Retorna os objetos armazenados no banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Tudo certo!"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Requisição mal-feita!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Usuário não autorizado!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Usuário não autenticado!"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Erro interno do servidor!")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        return bairroService.listar();
    }

    // ----- Consultar Por Id
    @GetMapping("/{id}")
    public ResponseEntity<?> consultar(@PathVariable(name = "id") Long codigoBairro) {
        return bairroService.consultar(codigoBairro);
    }

    // ----- Atualizar Por Id
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long codigoBairro, @RequestBody @Valid BairroDtoEntrada bairroDtoEntrada) {
        return bairroService.atualizar(codigoBairro, bairroDtoEntrada);
    }

    // ----- Deletar Por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable(name = "id") Long codigoBairro) {
        return bairroService.deletar(codigoBairro);
    }
}
