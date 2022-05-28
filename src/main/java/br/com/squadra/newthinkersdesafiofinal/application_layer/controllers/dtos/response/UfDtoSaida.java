package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities.Uf;

public final class UfDtoSaida {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoUf;
    private String sigla;
    private String nome;
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public UfDtoSaida() {}
    public UfDtoSaida(Uf uf) {
        setCodigoUf(uf.getCodigoUf());
        setNome(uf.getNome());
        setSigla(uf.getSigla());
        setStatus(uf.getStatus());
    }

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoUf() {
        return codigoUf;
    }

    public void setCodigoUf(Long codigoUf) {
        this.codigoUf = codigoUf;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
