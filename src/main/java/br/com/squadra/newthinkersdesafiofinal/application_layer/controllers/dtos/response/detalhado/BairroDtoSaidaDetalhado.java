package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.detalhado;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Bairro;

public final class BairroDtoSaidaDetalhado {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoBairro;
    private String nome;
    private Integer status;
    private MunicipioDtoSaidaDetalhado municipio;

    // ---------- CONSTRUTORES ---------- //
    public BairroDtoSaidaDetalhado() {}
    public BairroDtoSaidaDetalhado(Bairro bairro) {
        setCodigoBairro(bairro.getCodigoBairro());
        setNome(bairro.getNome());
        setStatus(bairro.getStatus());
        setMunicipio(new MunicipioDtoSaidaDetalhado(bairro.getMunicipio()));
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

    public MunicipioDtoSaidaDetalhado getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioDtoSaidaDetalhado municipio) {
        this.municipio = municipio;
    }
}
