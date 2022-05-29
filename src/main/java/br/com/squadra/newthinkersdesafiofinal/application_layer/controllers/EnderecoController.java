package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.EnderecoDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid EnderecoDtoEntrada enderecoDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        return enderecoService.cadastrar(enderecoDtoEntrada, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return enderecoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultar(@PathVariable(name = "id") Long codigoEndereco) {
        return enderecoService.consultar(codigoEndereco);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long codigoEndereco, @RequestBody @Valid EnderecoDtoEntrada enderecoDtoEntrada) {
        return enderecoService.atualizar(codigoEndereco, enderecoDtoEntrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable(name = "id") Long codigoEndereco) {
        return enderecoService.deletar(codigoEndereco);
    }
}
