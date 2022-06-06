package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Uf;

public final class UfDtoSaida {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoUF;
    private String sigla;
    private String nome;
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public UfDtoSaida() {}
    public UfDtoSaida(Uf uf) {
        setCodigoUF(uf.getCodigoUF());
        setNome(uf.getNome());
        setSigla(uf.getSigla());
        setStatus(uf.getStatus());
    }

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Long codigoUF) {
        this.codigoUF = codigoUF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
