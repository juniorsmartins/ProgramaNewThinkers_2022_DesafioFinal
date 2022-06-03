package br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist;

import org.hibernate.annotations.SQLDelete;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_uf")
@SQLDelete(sql = "UPDATE tb_uf SET status = 2 WHERE codigo_UF = ?;")
public final class Uf implements Serializable {

    // ---------- ATRIBUTOS DE CLASSE ---------- //
    private static final Long serialVersionUID = 1L;

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Codigo_UF", nullable = false)
    private Long codigoUF;
    @Column(name = "nome", length = 60, nullable = false, unique = true)
    private String nome;
    @Column(name = "sigla", length = 2, nullable = false, unique = true)
    private String sigla;
    @Column(name = "status", length = 1, nullable = false)
    private Integer status;
    // ----- Relacionamento Bidirecional
    @OneToMany(mappedBy = "uf", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Municipio> municipio;

    // ---------- CONSTRUTORES ---------- //
    public Uf() {}

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

    public List<Municipio> getMunicipio() {
        return municipio;
    }

    public void setMunicipio(List<Municipio> municipio) {
        this.municipio = municipio;
    }
}
