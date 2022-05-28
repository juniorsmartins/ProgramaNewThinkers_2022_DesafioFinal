package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class MunicipioDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @NotNull @NotEmpty @Length(max = 256)
    private String nome;

    // ---------- CONSTRUTORES ---------- //
    public MunicipioDtoEntrada() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
