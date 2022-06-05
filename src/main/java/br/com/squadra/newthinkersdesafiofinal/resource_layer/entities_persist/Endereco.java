package br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist;

import org.hibernate.annotations.SQLDelete;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_endereco")
@SQLDelete(sql = "UPDATE tb_endereco SET status = 2 WHERE codigo_endereco = ?;")
public final class Endereco implements Serializable {

    // ---------- ATRIBUTOS DE CLASSE ---------- //
    private static final Long serialVersionUID = 1L;

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_endereco", nullable = false)
    private Long codigoEndereco;
    @Column(name = "cep", length = 10, nullable = false)
    private String cep;
    @Column(name = "nome_rua", length = 256, nullable = false)
    private String nomeRua;
    @Column(name = "numero", length = 10, nullable = false)
    private Integer numero;
    @Column(name = "complemento", length = 20)
    private String complemento;
    // ----- Relacionamento Bidirecional
    @ManyToOne
    @JoinColumn(name = "codigo_bairro", referencedColumnName = "codigo_bairro", nullable = false)
    private Bairro bairro;
    @ManyToOne
    @JoinColumn(name = "codigo_pessoa", referencedColumnName = "codigo_pessoa", nullable = false)
    private Pessoa pessoa;

    // ---------- CONSTRUTORES ---------- //
    public Endereco() {}

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

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
