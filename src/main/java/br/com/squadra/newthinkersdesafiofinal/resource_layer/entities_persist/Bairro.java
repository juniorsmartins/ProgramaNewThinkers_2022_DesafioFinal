package br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_bairro")
public final class Bairro implements Serializable {

    // ---------- ATRIBUTOS DE CLASSE ---------- //
    private static final Long serialVersionUID = 1L;

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_bairro", nullable = false)
    private Long codigoBairro;
    @Column(name = "nome", length = 256, nullable = false)
    private String nome;
    @Column(name = "status", length = 3, nullable = false)
    private Integer status;
    // ----- Relacionamento Bidirecional
    @ManyToOne
    @JoinColumn(name = "codigo_municipio", referencedColumnName = "codigo_municipio", nullable = false)
    private Municipio municipio;

    // ---------- CONSTRUTORES ---------- //
    public Bairro() {}

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

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
}
