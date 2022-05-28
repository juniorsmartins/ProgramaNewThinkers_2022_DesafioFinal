package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.PessoaDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid PessoaDtoEntrada pessoaDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        return pessoaService.cadastrar(pessoaDtoEntrada, uriComponentsBuilder);
    }

    @PutMapping
    public ResponseEntity<?> atualizar() {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> listar(String sobrenome) {
        return null;
    }

    @GetMapping("/{codigoPessoa}")
    public ResponseEntity<?> buscar(Long codigoPessoa) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<?> deletar() {
        return null;
    }
}
