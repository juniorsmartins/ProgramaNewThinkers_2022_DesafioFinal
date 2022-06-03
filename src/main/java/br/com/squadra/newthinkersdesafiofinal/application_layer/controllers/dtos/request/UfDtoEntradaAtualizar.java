package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class UfDtoEntradaAtualizar {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    @NotNull(message = "CodigoUF - Preenchimento obrigatório! Não pode ser nulo.")
    private Long codigoUF;
    @Schema(description = "Denominação", type = "String", example = "Paraná", required = true)
    @NotBlank(message = "Nome - Preenchimento obrigatório! Não pode ser nulo ou vazio.")
    @Length(max = 60, message = "Nome - Preenchimento máximo de 60 dígitos.")
    private String nome;
    @Schema(description = "Abreviatura da denominação", type = "String", example = "PR", required = true)
    @NotBlank(message = "Sigla - Preenchimento obrigatório! Não pode ser nulo ou vazio.")
    @Length(min = 2, max = 2, message = "Sigla - Preenchimento obrigatório de mínimo e de máximo dois dígitos.")
    private String sigla;
    @Schema(description = "Status 1 para Ativado e 2 para Desativado.", type = "Long", example = "1")
    @NotNull(message = "Status - Preenchimento obrigatório!") @Max(1)
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public UfDtoEntradaAtualizar() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Long codigoUF) {
        this.codigoUF = codigoUF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
