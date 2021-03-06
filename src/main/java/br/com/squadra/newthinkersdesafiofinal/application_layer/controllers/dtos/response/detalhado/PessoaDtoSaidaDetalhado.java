package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.response.detalhado;

import br.com.squadra.newthinkersdesafiofinal.resource_layer.entities_persist.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public final class PessoaDtoSaidaDetalhado {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoPessoa;
    private String nome;
    private String sobrenome;
    private Integer idade;
    private String login;
    @JsonIgnore
    private String senha;
    private Integer status;
    private List<EnderecoDtoSaidaDetalhado> enderecos;

    // ---------- CONSTRUTORES ---------- //
    public PessoaDtoSaidaDetalhado() {}
    public PessoaDtoSaidaDetalhado(Pessoa pessoa) {
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
                    .map(EnderecoDtoSaidaDetalhado::new)
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

    public List<EnderecoDtoSaidaDetalhado> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDtoSaidaDetalhado> enderecos) {
        this.enderecos = enderecos;
    }
}
