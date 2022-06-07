package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class UfDtoEntradaAtualizar {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    @NotNull(message = "{campo.codigo-uf.naonulo}")
    @Positive(message = "{campo.codigo-qualquer.numeropositivo}")
    private Long codigoUF;
    @Schema(description = "Abreviatura da denominação", type = "String", example = "PR", required = true)
    @NotBlank(message = "{anotacao.notblank.padrao}")
    @Length(min = 2, max = 2, message = "{campo.sigla.tamanho}")
    private String sigla;
    @Schema(description = "Denominação", type = "String", example = "Paraná", required = true)
    @NotBlank(message = "{anotacao.notblank.padrao}")
    @Length(max = 60, message = "{campo.nome.tamanho}")
    private String nome;
    @Schema(description = "Status 1 para Ativado e 2 para Desativado.", type = "Long", example = "1")
    @NotNull(message = "{anotacao.notnull.padrao}")
    @Max(value = 2, message = "{campo.status.tamanho}")
    @Min(value = 1, message = "{campo.status.tamanho}")
    private Integer status;

    // ---------- CONSTRUTORES ---------- //
    public UfDtoEntradaAtualizar() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Long codigoUF) {
        this.codigoUF = codigoUF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
