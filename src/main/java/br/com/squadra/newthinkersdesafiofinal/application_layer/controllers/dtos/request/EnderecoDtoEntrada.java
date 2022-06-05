package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class EnderecoDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    private Long codigoEndereco;
    @Schema(description = "Código de Endereçamento Postal", type = "String", example = "86044-648", required = true)
    @NotNull @NotEmpty @Length(max = 10)
    private String cep;
    @Schema(description = "Denominação", type = "String", example = "Rua Eliza Michelete Vicente", required = true)
    @NotBlank @Length(max = 256)
    private String nomeRua;
    @Schema(description = "Número", type = "Integer", example = "2158", required = true)
    @NotNull @Max(99999)
    private Integer numero;
    @Schema(description = "Informações adicionais", type = "String", example = "Entrada pela lateral")
    @Length(max = 20)
    private String complemento;
    @Schema(description = "Status 1 para Ativado e 0 para Desativado.", type = "Long", example = "1")
    @NotNull @Max(1)
    private Integer status;
    @Schema(description = "Chave Identificadora", type = "Long", example = "15", required = true)
    @NotNull
    private Long codigoBairro;
    @Schema(description = "Chave Identificadora", type = "Long", example = "17")
    private Long codigoPessoa;

    // ---------- CONSTRUTORES ---------- //
    public EnderecoDtoEntrada() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoEndereco() {
        return codigoEndereco;
    }

    public void setCodigoEndereco(Long codigoEndereco) {
        this.codigoEndereco = codigoEndereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCodigoBairro() {
        return codigoBairro;
    }

    public void setCodigoBairro(Long codigoBairro) {
        this.codigoBairro = codigoBairro;
    }

    public Long getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Long codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }
}
