package br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_municipio")
public final class Municipio implements Serializable {

    // ---------- ATRIBUTOS DE CLASSE ---------- //
    private static final Long serialVersionUID = 1L;

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_municipio", nullable = false)
    private Long codigoMunicipio;
    @Column(name = "nome", length = 256, nullable = false)
    private String nome;
    @Column(name = "status", length = 3, nullable = false)
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public Municipio() {}

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
