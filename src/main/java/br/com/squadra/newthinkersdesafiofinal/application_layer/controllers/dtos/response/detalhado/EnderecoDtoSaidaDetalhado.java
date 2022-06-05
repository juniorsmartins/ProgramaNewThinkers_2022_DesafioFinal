package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.detalhado;

import br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.BairroDtoSaida;
import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Endereco;

public final class EnderecoDtoSaidaDetalhado {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoEndereco;
    private String cep;
    private String nomeRua;
    private Integer numero;
    private String complemento;
    private BairroDtoSaida bairro;
    private Long codigoPessoa;

    // ---------- CONSTRUTORES ---------- //
    public EnderecoDtoSaidaDetalhado() {}
    public EnderecoDtoSaidaDetalhado(Endereco endereco) {
        setCodigoEndereco(endereco.getCodigoEndereco());
        setCep(endereco.getCep());
        setNomeRua(endereco.getNomeRua());
        setNumero(endereco.getNumero());
        setComplemento(endereco.getComplemento());
        setBairro(new BairroDtoSaida(endereco.getBairro()));
    }

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoEndereco() {
        return codigoEndereco;
    }

    public void setCodigoEndereco(Long codigoEndereco) {
        this.codigoEndereco = codigoEndereco;
    }

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

    public BairroDtoSaida getBairro() {
        return bairro;
    }

    public void setBairro(BairroDtoSaida bairro) {
        this.bairro = bairro;
    }

    public Long getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Long codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }
}
