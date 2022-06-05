package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.detalhado;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.UfDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Municipio;

public final class MunicipioDtoSaidaDetalhado {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoMunicipio;
    private String nome;
    private Integer status;
    private UfDtoSaida uf;

    // ---------- CONSTRUTORES ---------- //
    public MunicipioDtoSaidaDetalhado() {}
    public MunicipioDtoSaidaDetalhado(Municipio municipio) {
        setCodigoMunicipio(municipio.getCodigoMunicipio());
        setNome(municipio.getNome());
        setStatus(municipio.getStatus());
        setUf(new UfDtoSaida(municipio.getUf()));
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

    public UfDtoSaida getUf() {
        return uf;
    }

    public void setUf(UfDtoSaida uf) {
        this.uf = uf;
    }
}
