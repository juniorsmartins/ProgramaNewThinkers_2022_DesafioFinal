package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Normalized;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class MunicipioDtoEntradaAtualizar {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    @NotNull(message = "{campo.codigo-municipio.naonulo}")
    @Positive(message = "{campo.codigo-qualquer.numeropositivo}")
    private Long codigoMunicipio;
    @Schema(description = "Chave Identificadora", type = "Long", example = "18", required = true)
    @NotNull(message = "{campo.codigo-uf.naonulo}")
    @Positive(message = "{campo.codigo-qualquer.numeropositivo}")
    private Long codigoUF;
    @Schema(description = "Denominação", type = "String", example = "Londrina", required = true)
    @NotBlank(message = "{campo.nome.naonuloandnaovazio}")
    @Length(max = 256, message = "{campo.nome.nome-municipio-tamanho}")
    private String nome;
    @Schema(description = "Status 1 para Ativado e 2 para Desativado.", type = "Long", example = "1")
    @NotNull(message = "{campo.status.obrigatorio}")
    @Max(value = 2, message = "{campo.status.tamanho}")
    @Min(value = 1, message = "{campo.status.tamanho}")
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public MunicipioDtoEntradaAtualizar() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Long codigoUF) {
        this.codigoUF = codigoUF;
    }

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
}
