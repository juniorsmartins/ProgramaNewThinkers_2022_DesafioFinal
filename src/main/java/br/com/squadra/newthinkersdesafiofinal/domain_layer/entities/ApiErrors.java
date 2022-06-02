package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities;

import lombok.Data;
import java.util.Arrays;
import java.util.List;

@Data
public class ApiErrors {

    private List<String> errors;

    public ApiErrors(String mensagemDeErro) {
        errors = Arrays.asList(mensagemDeErro);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}
