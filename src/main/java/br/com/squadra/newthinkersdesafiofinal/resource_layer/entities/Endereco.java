package br.com.squadra.newthinkersdesafiofinal.resource_layer.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_endereco")
public final class Endereco implements Serializable {

    // ---------- ATRIBUTOS DE CLASSE ---------- //
    private static final Long serialVersionUID = 1L;

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_endereco", nullable = false)
    private Long codigoEndereco;
    @Column(name = "nome_rua", length = 256, nullable = false)
    private String nomeRua;
    @Column(name = "numero", length = 10, nullable = false)
    private Integer numero;
    @Column(name = "complemento", length = 20)
    private String complemento;
    @Column(name = "cep", length = 10, nullable = false)
    private String cep;

    // ---------- CONSTRUTORES ---------- //
    public Endereco() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
