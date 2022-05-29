package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.BairroDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.services.BairroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/bairros")
public class BairroController {

    @Autowired
    private BairroService bairroService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid BairroDtoEntrada bairroDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        return bairroService.cadastrar(bairroDtoEntrada, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return bairroService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultar(@PathVariable(name = "id") Long codigoBairro) {
        return bairroService.consultar(codigoBairro);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long codigoBairro, @RequestBody @Valid BairroDtoEntrada bairroDtoEntrada) {
        return bairroService.atualizar(codigoBairro, bairroDtoEntrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable(name = "id") Long codigoBairro) {
        return bairroService.deletar(codigoBairro);
    }
}
