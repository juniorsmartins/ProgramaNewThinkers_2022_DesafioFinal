package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class UfDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Abreviatura da denominação", type = "String", example = "PR", required = true)
    @NotNull @NotEmpty @Length(max = 2)
    private String sigla;
    @Schema(description = "Denominação", type = "String", example = "Paraná", required = true)
    @NotNull @NotEmpty @Length(max = 60)
    private String nome;

    // ---------- CONSTRUTORES ---------- //
    public UfDtoEntrada() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
