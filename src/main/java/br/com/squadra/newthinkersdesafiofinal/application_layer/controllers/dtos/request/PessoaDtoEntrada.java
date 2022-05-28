package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;

public final class PessoaDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @NotBlank @Length(max = 256)
    private String nome;
    @NotBlank @Length(max = 256)
    private String sobrenome;
    @NotNull @Max(150)
    private Integer idade;
    @NotBlank @Length(max = 50)
    private String login;
    @NotBlank @Length(max = 50)
    private String senha;

    // ---------- CONSTRUTORES ---------- //
    public PessoaDtoEntrada() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
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

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
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
}
