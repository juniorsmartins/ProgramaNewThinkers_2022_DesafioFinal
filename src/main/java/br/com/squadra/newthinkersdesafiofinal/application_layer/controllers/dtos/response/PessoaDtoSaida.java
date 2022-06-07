package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public final class PessoaDtoSaida {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoPessoa;
    private String nome;
    private String sobrenome;
    private Integer idade;
    private String login;
    @JsonIgnore
    private String senha;
    private Integer status;
    @JsonIgnore
    private List<EnderecoDtoSaida> enderecos;

    // ---------- CONSTRUTORES ---------- //
    public PessoaDtoSaida() {}
    public PessoaDtoSaida(Pessoa pessoa) {
        setCodigoPessoa(pessoa.getCodigoPessoa());
        setNome(pessoa.getNome());
        setSobrenome(pessoa.getSobrenome());
        setIdade(pessoa.getIdade());
        setLogin(pessoa.getLogin());
        setSenha(pessoa.getSenha());
        setStatus(pessoa.getStatus());
        if(pessoa.getEnderecos() != null)
            setEnderecos(pessoa.getEnderecos()
                    .stream()
                    .map(EnderecoDtoSaida::new)
                    .toList());
    }

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

    public List<EnderecoDtoSaida> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDtoSaida> enderecos) {
        this.enderecos = enderecos;
    }
}
