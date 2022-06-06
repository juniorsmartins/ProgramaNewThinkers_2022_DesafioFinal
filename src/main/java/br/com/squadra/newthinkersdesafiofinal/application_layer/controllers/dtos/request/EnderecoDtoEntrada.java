package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class EnderecoDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    private Long codigoEndereco;
    @Schema(description = "Código de Endereçamento Postal", type = "String", example = "86044-648", required = true)
    @NotBlank(message = "{campo.cep.naonuloandnaovazio}")
    @Positive(message = "{campo.codigo-qualquer.numeropositivo}")
    private String cep;
    @Schema(description = "Chave Identificadora", type = "Long", example = "15", required = true)
    @NotNull(message = "{campo.codigo-bairro.naonulo}")
    @Positive(message = "{campo.codigo-qualquer.numeropositivo}")
    private Long codigoBairro;
    @Schema(description = "Denominação", type = "String", example = "Rua Eliza Michelete Vicente", required = true)
    @NotBlank(message = "{campo.nomerua.naonuloandnaovazio}")
    @Length(max = 256, message = "{campo.nomerua.tamanho}")
    private String nomeRua;
    @Schema(description = "Número", type = "Integer", example = "2158", required = true)
    @NotNull(message = "{campo.numero.naonulo}")
    @Max(value = 99999, message = "{campo.numero.tamanho}")
    @Positive(message = "{campo.numbers.numeropositivo}")
    private Integer numero;
    @Schema(description = "Informações adicionais", type = "String", example = "Entrada pela lateral")
    @Length(max = 20, message = "{campo.complemento.tamanho}")
    private String complemento;

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

    public Long getCodigoBairro() {
        return codigoBairro;
    }

    public void setCodigoBairro(Long codigoBairro) {
        this.codigoBairro = codigoBairro;
    }
}
