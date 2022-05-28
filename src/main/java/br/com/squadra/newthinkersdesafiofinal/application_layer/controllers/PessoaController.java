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

    @GetMapping
    public ResponseEntity<?> listar() {
        return pessoaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultar(@PathVariable(name = "id") Long codigoPessoa) {
        return pessoaService.consultar(codigoPessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long codigoPessoa, @RequestBody @Valid PessoaDtoEntrada pessoaDtoEntrada) {
        return pessoaService.atualizar(codigoPessoa, pessoaDtoEntrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable(name = "id") Long codigoPessoa) {
        return pessoaService.deletar(codigoPessoa);
    }
}
