package br.com.squadra.newthinkersdesafiofinal.application_layer.controllers.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Classe de transporte e validação de dados de entrada.")
public final class BairroDtoEntrada {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    @Schema(description = "Denominação", type = "String", example = "Centro Sul", required = true)
    @NotBlank @Length(max = 256)
    private String nome;
    @Schema(description = "Chave Identificadora", type = "Long", example = "10", required = true)
    @NotNull
    private Long codigoMunicipio;

    // ---------- CONSTRUTORES ---------- //
    public BairroDtoEntrada() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Long codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }
}
