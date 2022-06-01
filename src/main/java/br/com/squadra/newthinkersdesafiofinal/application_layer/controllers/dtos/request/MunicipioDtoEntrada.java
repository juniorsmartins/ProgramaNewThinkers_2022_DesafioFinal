package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class MunicipioDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Chave Identificadora", type = "Long", example = "18")
    private Long codigoMunicipio;
    @Schema(description = "Denominação", type = "String", example = "Londrina", required = true)
    @NotNull @NotEmpty @Length(max = 256)
    private String nome;
    @Schema(description = "Status 1 para Ativado e 0 para Desativado.", type = "Long", example = "1")
    private Integer status;
    @Schema(description = "Chave Identificadora", type = "Long", example = "18", required = true)
    @NotNull
    private Long codigoUf;

    // ---------- CONSTRUTORES ---------- //
    public MunicipioDtoEntrada() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
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

    public Long getCodigoUf() {
        return codigoUf;
    }

    public void setCodigoUf(Long codigoUf) {
        this.codigoUf = codigoUf;
    }
}
