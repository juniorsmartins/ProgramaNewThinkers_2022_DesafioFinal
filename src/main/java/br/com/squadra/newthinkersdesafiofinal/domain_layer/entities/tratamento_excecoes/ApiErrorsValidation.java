package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Classe para personalizar retorno de exceções.")
public final class ApiErrorsValidation {

    @Schema(description = "Código Http", type = "String", example = "400 BAD_REQUEST")
    private String status;
    @Schema(description = "Explicação padrão", type = "String", example = "não deve estar em branco.")
    private String mensagem;
    @Schema(description = "Mostra qual anotação lançou restrição.", type = "String", example = "@NotNull")
    private String anotacao;
    @Schema(description = "Mostra qual atributo/campo gerou restrição.", type = "String", example = "Status")
    private String nomeDoCampo;

    public ApiErrorsValidation(String status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }

    public ApiErrorsValidation(String status, String mensagem, String nomeDoCampo, String anotacao) {
        this(status, mensagem);
        this.nomeDoCampo = nomeDoCampo;
        this.anotacao = anotacao;
    }
}
