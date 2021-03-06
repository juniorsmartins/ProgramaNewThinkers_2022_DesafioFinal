package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Endereco;

public final class EnderecoDtoSaida {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoEndereco;
    private String cep;
    private String nomeRua;
    private Integer numero;
    private String complemento;
    private Long codigoBairro;
    private Long codigoPessoa;

    // ---------- CONSTRUTORES ---------- //
    public EnderecoDtoSaida() {}
    public EnderecoDtoSaida(Endereco endereco) {
        setCodigoEndereco(endereco.getCodigoEndereco());
        setCep(endereco.getCep());
        setNomeRua(endereco.getNomeRua());
        setNumero(endereco.getNumero());
        setComplemento(endereco.getComplemento());
        setCodigoBairro(endereco.getBairro().getCodigoBairro());
        setCodigoPessoa(endereco.getPessoa().getCodigoPessoa());
    }

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getCodigoEndereco() {
        return codigoEndereco;
    }

    public void setCodigoEndereco(Long codigoEndereco) {
        this.codigoEndereco = codigoEndereco;
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

    public Long getCodigoBairro() {
        return codigoBairro;
    }

    public void setCodigoBairro(Long codigoBairro) {
        this.codigoBairro = codigoBairro;
    }

    public Long getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Long codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }
}
