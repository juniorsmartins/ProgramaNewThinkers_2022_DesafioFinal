package br.com.squadra.newthinkersdesafiofinal.application_layer.configs.validation;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErroDeValidation {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Código Http", type = "String", example = "400 BAD_REQUEST")
    private String status;
    @Schema(description = "Anotação origem da invalidação", type = "String", example = "NotBlank")
    private String anotacao;
    @Schema(description = "Campo origem da invalidação", type = "String", example = "NomeRua")
    private String campo;
    @Schema(description = "Explicação padrão", type = "String", example = "não deve estar em branco")
    private String mensagem;

    // ---------- CONSTRUTORES ---------- //
    public ErroDeValidation(String status, String anotacao, String campo, String mensagem) {
        this.status = status;
        this.anotacao = anotacao;
        this.campo = campo;
        this.mensagem = mensagem;
    }

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
