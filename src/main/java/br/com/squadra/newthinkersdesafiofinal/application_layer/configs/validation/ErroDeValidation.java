package br.com.squadra.newthinkersdesafiofinal.application_layer.configs.validation;

public class ErroDeValidation {

    private String status;
    private String anotacao;
    private String campo;
    private String mensagem;

    public ErroDeValidation(String status, String anotacao, String campo, String mensagem) {
        this.status = status;
        this.anotacao = anotacao;
        this.campo = campo;
        this.mensagem = mensagem;
    }

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
