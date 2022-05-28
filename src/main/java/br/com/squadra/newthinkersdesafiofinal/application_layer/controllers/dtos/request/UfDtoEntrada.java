package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class UfDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @NotNull @NotEmpty @Length(max = 2)
    private String sigla;
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
