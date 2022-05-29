package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.EnderecoDtoSaida;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class EnderecoDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @NotNull @NotEmpty @Length(max = 10)
    private String cep;
    @NotBlank @Length(max = 256)
    private String nomeRua;
    @NotNull @Max(99999)
    private Integer numero;
    @Length(max = 20)
    private String complemento;

    // ---------- CONSTRUTORES ---------- //
    public EnderecoDtoEntrada() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNomeRua() {
        return nomeRua;
    }

    public void setNomeRua(String nomeRua) {
        this.nomeRua = nomeRua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
