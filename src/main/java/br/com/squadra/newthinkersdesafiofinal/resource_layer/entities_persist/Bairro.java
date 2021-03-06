package br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_bairro")
public final class Bairro implements Serializable {

    // ---------- ATRIBUTOS DE CLASSE ---------- //
    private static final Long serialVersionUID = 1L;

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BAIRRO_SEQUENCE_NAME")
    @SequenceGenerator(name = "BAIRRO_SEQUENCE_NAME", sequenceName = "BAIRRO_SEQ_NAME",
            initialValue = 1, allocationSize = 1)
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
    @OneToMany(mappedBy = "bairro", cascade = {CascadeType.ALL, CascadeType.PERSIST}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Endereco> endereco;

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

    public List<Endereco> getEndereco() {
        return endereco;
    }

    public void setEndereco(List<Endereco> endereco) {
        this.endereco = endereco;
    }
}
