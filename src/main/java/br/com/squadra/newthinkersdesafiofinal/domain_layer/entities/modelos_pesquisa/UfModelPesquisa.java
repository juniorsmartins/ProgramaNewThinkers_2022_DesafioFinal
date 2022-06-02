package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.modelos_pesquisa;

public final class UfModelPesquisa {

    // ---------- ATRIBUTOS DE INSTÂNCIA ---------- //
    private Long codigoUF;
    private String sigla;
    private String nome;

    // ---------- CONSTRUTORES ---------- //
    public UfModelPesquisa() {}

    // ---------- MÉTODOS GETTERS E SETTERS ---------- //
    public Long getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Long codigoUF) {
        this.codigoUF = codigoUF;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
