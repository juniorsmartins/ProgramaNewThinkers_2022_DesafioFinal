package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class PessoaDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    private Long codigoPessoa;
    @Schema(description = "Denominação pessoal", type = "String", example = "Andrew", required = true)
    @NotBlank @Length(max = 256)
    private String nome;
    @Schema(description = "Denominação familiar", type = "String", example = "Hunt", required = true)
    @NotBlank @Length(max = 256)
    private String sobrenome;
    @Schema(description = "Anos", type = "Integer", example = "32", required = true)
    @NotNull @Max(150)
    private Integer idade;
    @Schema(description = "Identificação", type = "String", example = "huntandrew", required = true)
    @NotBlank @Length(max = 50)
    private String login;
    @Schema(description = "Código secreto", type = "String", example = "hunt123456", required = true)
    @NotBlank @Length(max = 50)
    private String senha;
    @Schema(description = "Status 1 para Ativado e 0 para Desativado.", type = "Long", example = "1")
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public PessoaDtoEntrada() {}

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
