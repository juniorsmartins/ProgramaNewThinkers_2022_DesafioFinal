package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class MunicipioDtoEntradaListar {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "String", example = "17")
    private String codigoMunicipio;
    @Schema(description = "Chave Identificadora", type = "String", example = "17", required = true)
    private String codigoUF;
    @Schema(description = "Denominação", type = "String", example = "Londrina", required = true)
    private String nome;
    @Schema(description = "Status 1 para Ativado e 2 para Desativado.", type = "String", example = "1")
    private String status;

    // ---------- CONSTRUTORES ---------- //
    public MunicipioDtoEntradaListar() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
