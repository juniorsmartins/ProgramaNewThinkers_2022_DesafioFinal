package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Bairro;

public final class BairroDtoSaida {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoBairro;
    private Long codigoMunicipio;
    private String nome;
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public BairroDtoSaida() {}
    public BairroDtoSaida(Bairro bairro) {
        setCodigoBairro(bairro.getCodigoBairro());
        setNome(bairro.getNome());
        setStatus(bairro.getStatus());
        setCodigoMunicipio(bairro.getMunicipio().getCodigoMunicipio());
    }

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoBairro() {
        return codigoBairro;
    }

    public void setCodigoBairro(Long codigoBairro) {
        this.codigoBairro = codigoBairro;
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

    public Long getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Long codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }
}
