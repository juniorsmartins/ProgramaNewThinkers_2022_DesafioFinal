package br.com.squadra.newthinkersdesafiofinal.resource_layer.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_uf")
public final class Uf implements Serializable {

    // ---------- ATRIBUTOS DE CLASSE ---------- //
    private static final Long serialVersionUID = 1L;

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_uf", nullable = false)
    private Long codigoUf;
    @Column(name = "sigla", length = 3, nullable = false)
    private String sigla;
    @Column(name = "nome", length = 60, nullable = false)
    private String nome;
    @Column(name = "status", length = 3, nullable = false)
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public Uf() {}

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
