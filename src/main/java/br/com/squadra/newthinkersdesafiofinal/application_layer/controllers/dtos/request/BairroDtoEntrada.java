package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public final class BairroDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @NotBlank @Length(max = 256)
    private String nome;

    // ---------- CONSTRUTORES ---------- //
    public BairroDtoEntrada() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
