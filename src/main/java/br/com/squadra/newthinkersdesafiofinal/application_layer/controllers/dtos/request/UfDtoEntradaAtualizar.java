package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class UfDtoEntradaAtualizar {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    @NotNull(message = "{campo.codigouf.naonulo}")
    private Long codigoUF;
    @Schema(description = "Denominação", type = "String", example = "Paraná", required = true)
    @NotBlank(message = "{campo.nome.naonuloandnaovazio}")
    @Length(max = 60, message = "{campo.nome.tamanho}")
    private String nome;
    @Schema(description = "Abreviatura da denominação", type = "String", example = "PR", required = true)
    @NotBlank(message = "{campo.sigla.naonuloandnaovazio}")
    @Length(min = 2, max = 2, message = "{campo.sigla.tamanho}")
    private String sigla;
    @Schema(description = "Status 1 para Ativado e 2 para Desativado.", type = "Long", example = "1")
    @NotNull(message = "{campo.status.obrigatorio}") @Max(value = 1, message = "{campo.status.tamanho}")
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
