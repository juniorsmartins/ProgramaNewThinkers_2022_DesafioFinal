package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class UfDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    private String codigoUF;
    @Schema(description = "Abreviatura da denominação", type = "String", example = "PR", required = true)
    @NotNull @NotEmpty @Length(max = 2)
    private String sigla;
    @Schema(description = "Denominação", type = "String", example = "Paraná", required = true)
    @NotNull @NotEmpty @Length(max = 60)
    private String nome;
    @Schema(description = "Status 1 para Ativado e 0 para Desativado.", type = "Long", example = "1")
    @NotNull @Max(1)
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public UfDtoEntrada() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(String codigoUF) {
        this.codigoUF = codigoUF;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
