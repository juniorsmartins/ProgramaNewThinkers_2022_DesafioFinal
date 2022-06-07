package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class UfDtoEntradaListar {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "String", example = "18")
    private String codigoUF;
    @Schema(description = "Abreviatura da denominação", type = "String", example = "PR", required = true)
    private String sigla;
    @Schema(description = "Denominação", type = "String", example = "Paraná", required = true)
    private String nome;
    @Schema(description = "Status 1 para Ativado e 2 para Desativado.", type = "Long", example = "1")
    private String status;

    // ---------- CONSTRUTORES ---------- //
    public UfDtoEntradaListar() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(String codigoUF) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
