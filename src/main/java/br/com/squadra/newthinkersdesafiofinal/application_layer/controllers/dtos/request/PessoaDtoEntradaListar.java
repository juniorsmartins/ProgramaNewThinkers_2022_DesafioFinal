package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class PessoaDtoEntradaListar {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "String", example = "17")
    private String codigoPessoa;
    @Schema(description = "Denominação pessoal", type = "String", example = "Andrew", required = true)
    private String nome;
    @Schema(description = "Denominação familiar", type = "String", example = "Hunt", required = true)
    private String sobrenome;
    @Schema(description = "Anos de vida", type = "String", example = "17", required = true)
    private String idade;
    @Schema(description = "Identificação", type = "String", example = "huntandrew", required = true)
    private String login;
    @Schema(description = "Código secreto", type = "String", example = "hunt123456", required = true)
    private String senha;
    @Schema(description = "Status 1 para Ativado e 2 para Desativado.", type = "String", example = "17")
    private String status;

    // ---------- CONSTRUTORES ---------- //
    public PessoaDtoEntradaListar() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(String codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
