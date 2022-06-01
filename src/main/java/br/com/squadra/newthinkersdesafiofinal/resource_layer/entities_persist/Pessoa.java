package br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_pessoa")
public final class Pessoa implements Serializable {

    // ---------- ATRIBUTOS DE CLASSE ---------- //
    private static final Long serialVersionUID = 1L;

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_pessoa", nullable = false)
    private Long codigoPessoa;
    @Column(name = "nome", length = 256, nullable = false)
    private String nome;
    @Column(name = "sobrenome", length = 256, nullable = false)
    private String sobrenome;
    @Column(name = "idade", length = 3, nullable = false)
    private Integer idade;
    @Column(name = "login", length = 50, nullable = false, unique = true)
    private String login;
    @Column(name = "senha", length = 50, nullable = false)
    private String senha;
    @Column(name = "status", length = 3, nullable = false)
    private Integer status;
    // ----- Relacionamento Bidirecional
    @OneToMany(mappedBy = "pessoa", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Endereco> listaDeEnderecos;

    // ---------- CONSTRUTORES ---------- //
    public Pessoa() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Long codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Endereco> getListaDeEnderecos() {
        return listaDeEnderecos;
    }

    public void setListaDeEnderecos(List<Endereco> listaDeEnderecos) {
        this.listaDeEnderecos = listaDeEnderecos;
    }
}
