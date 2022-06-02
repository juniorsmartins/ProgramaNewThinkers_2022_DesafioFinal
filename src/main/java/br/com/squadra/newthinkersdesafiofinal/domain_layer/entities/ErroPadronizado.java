package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Classe para personalizar retorno de erros.")
public class ErroPadronizado {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Código Http", type = "String", example = "400 BAD_REQUEST")
    private String status;
    @Schema(description = "Explicação padrão", type = "String", example = "não deve estar em branco")
    private String mensagem;

    // ---------- CONSTRUTORES ---------- //
    public ErroPadronizado(String status, String mensagem) {
        setStatus(status);
        setMensagem(mensagem);
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

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
