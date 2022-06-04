package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class PessoaDtoEntradaAtualizar {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    @NotNull(message = "{campo.codigo-pessoa.naonulo}")
    private Long codigoPessoa;
    @Schema(description = "Denominação pessoal", type = "String", example = "Andrew", required = true)
    @NotBlank(message = "{campo.nome.naonuloandnaovazio}")
    @Length(max = 256, message = "{campo.nome.tamanhoMaximo256}")
    private String nome;
    @Schema(description = "Denominação familiar", type = "String", example = "Hunt", required = true)
    @NotBlank(message = "{campo.sobrenome.naonuloandnaovazio}")
    @Length(max = 256, message = "{campo.sobrenome.tamanhoMaximo256}")
    private String sobrenome;
    @Schema(description = "Anos", type = "Integer", example = "32", required = true)
    @NotNull(message = "{campo.idade.naonulo}")
    @Max(value = 150, message = "{campo.idade.valorMaximo}")
    private Integer idade;
    @Schema(description = "Identificação", type = "String", example = "huntandrew", required = true)
    @NotBlank(message = "{campo.login.naonuloandnaovazio}")
    @Length(max = 50, message = "{campo.login.tamanhoMaximo}")
    private String login;
    @Schema(description = "Código secreto", type = "String", example = "hunt123456", required = true)
    @NotBlank(message = "{campo.senha.naonuloandnaovazio}")
    @Length(max = 50, message = "{campo.senha.tamanhoMaximo}")
    private String senha;
    @Schema(description = "Status 1 para Ativado e 2 para Desativado.", type = "Long", example = "1")
    @NotNull(message = "{campo.status.obrigatorio}")
    @Max(value = 2, message = "{campo.status.tamanho}")
    @Min(value = 1, message = "{campo.status.tamanho}")
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public PessoaDtoEntradaAtualizar() {}

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
}