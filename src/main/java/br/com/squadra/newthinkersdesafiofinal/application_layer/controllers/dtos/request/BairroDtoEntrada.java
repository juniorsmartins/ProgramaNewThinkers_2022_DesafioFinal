package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class BairroDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    private Long codigoBairro;
    @Schema(description = "Chave Identificadora", type = "Long", example = "10", required = true)
    @NotNull(message = "{campo.codigo-municipio.naonulo}")
    @Positive(message = "{campo.codigo-qualquer.numeropositivo}")
    private Long codigoMunicipio;
    @Schema(description = "Denominação", type = "String", example = "Centro Sul", required = true)
    @NotBlank(message = "{anotacao.notblank.padrao}")
    @Length(max = 256, message = "{campo.nome.nome-municipio-tamanho}")
    private String nome;
    @Schema(description = "Status 1 para Ativado e 2 para Desativado.", type = "Long", example = "1")
    @NotNull(message = "{anotacao.notnull.padrao}")
    @Max(value = 2, message = "{campo.status.tamanho}")
    @Min(value = 1, message = "{campo.status.tamanho}")
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public BairroDtoEntrada() {}

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

    public Long getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Long codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }
}
