package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.UfDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.services.UfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/ufs")
public class UfController {

    @Autowired
    private UfService ufService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid UfDtoEntrada ufDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        return ufService.cadastrar(ufDtoEntrada, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ufService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultar(@PathVariable(name = "id") Long codigoUf) {
        return ufService.consultar(codigoUf);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long codigoUf, @RequestBody @Valid UfDtoEntrada ufDtoEntrada) {
        return ufService.atualizar(codigoUf, ufDtoEntrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable(name = "id") Long codigoUf) {
        return ufService.deletar(codigoUf);
    }
}
