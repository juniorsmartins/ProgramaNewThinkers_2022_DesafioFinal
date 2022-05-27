package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PessoaController {

    @PostMapping
    public ResponseEntity<?> cadastrar() {
        return null;
    }
}
