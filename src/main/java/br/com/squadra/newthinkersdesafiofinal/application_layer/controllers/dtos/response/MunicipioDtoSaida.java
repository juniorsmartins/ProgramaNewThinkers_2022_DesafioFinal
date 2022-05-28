package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities.Municipio;

public final class MunicipioDtoSaida {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoMunicipio;
    private String nome;
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public MunicipioDtoSaida() {}
    public MunicipioDtoSaida(Municipio municipio) {
        setCodigoMunicipio(municipio.getCodigoMunicipio());
        setNome(municipio.getNome());
        setStatus(municipio.getStatus());
    }

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Long codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
