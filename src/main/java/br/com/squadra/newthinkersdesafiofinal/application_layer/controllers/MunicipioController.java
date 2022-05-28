package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request.MunicipioDtoEntrada;
import br.com.squadra.newthinkersdesafiofinal.domain_layer.services.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioService municipioService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid MunicipioDtoEntrada municipioDtoEntrada, UriComponentsBuilder uriComponentsBuilder) {
        return municipioService.cadastrar(municipioDtoEntrada, uriComponentsBuilder);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return municipioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultar(@PathVariable(name = "id") Long codigoMunicipio) {
        return municipioService.consultar(codigoMunicipio);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@PathVariable(name = "id") Long codigoMunicipio, @RequestBody @Valid MunicipioDtoEntrada municipioDtoEntrada) {
        return municipioService.atualizar(codigoMunicipio, municipioDtoEntrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable(name = "id") Long codigoMunicipio) {
        return municipioService.deletar(codigoMunicipio);
    }
}
