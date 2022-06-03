package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Arrays;
import java.util.List;

@Data
@Schema(description = "Classe para personalizar retorno de exceções.")
public class ApiErrors {

    @Schema(description = "Código Http", type = "String", example = "400 BAD_REQUEST")
    private String status;
    @Schema(description = "Explicação padrão", type = "String", example = "não deve estar em branco.")
    private String mensagem;

    public ApiErrors(String status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }
}
