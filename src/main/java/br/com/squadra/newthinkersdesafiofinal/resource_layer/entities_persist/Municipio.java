package br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_municipio")
@SQLDelete(sql = "UPDATE tb_municipio SET status = 2 WHERE codigo_municipio = ?;")
public final class Municipio implements Serializable {

    // ---------- ATRIBUTOS DE CLASSE ---------- //
    private static final Long serialVersionUID = 1L;
    private static final String MUNICIPIO_SEQUENCE_NAME = "UF_SEQUENCE_ID";

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = MUNICIPIO_SEQUENCE_NAME)
    @SequenceGenerator(name = MUNICIPIO_SEQUENCE_NAME, sequenceName = MUNICIPIO_SEQUENCE_NAME,
            initialValue = 1, allocationSize = 20)
    @Column(name = "codigo_municipio", nullable = false)
    private Long codigoMunicipio;
    @Column(name = "nome", length = 256, nullable = false, unique = true)
    private String nome;
    @Column(name = "status", length = 3, nullable = false)
    private Integer status;
    // ----- Relacionamento Bidirecional
    @ManyToOne
    @JoinColumn(name = "codigo_uf", referencedColumnName = "codigo_uf", nullable = false)
    private Uf uf;
    @OneToMany(mappedBy = "municipio", cascade = {CascadeType.PERSIST}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Bairro> listaDeBairros;

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

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }

    public List<Bairro> getListaDeBairros() {
        return listaDeBairros;
    }

    public void setListaDeBairros(List<Bairro> listaDeBairros) {
        this.listaDeBairros = listaDeBairros;
    }
}
