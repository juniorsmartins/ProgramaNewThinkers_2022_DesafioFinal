package br.com.squadra.newthinkersdesafiofinal.domain_layer.entities.tratamento_excecoes;

public final class MensagemPadrao {
    public static final String CODIGOUF_NAO_ENCONTRADO = "CodigoUF - Recurso não encontrado no banco de dados!";
    public static final String CODIGOMUNICIPIO_NAO_ENCONTRADO = "CodigoMunicipio - Recurso não encontrado no banco de dados!";
    public static final String CODIGOBAIRRO_NAO_ENCONTRADO = "CodigoBairro - Recurso não encontrado no banco de dados!";
    public static final String CODIGOPESSOA_NAO_ENCONTRADO = "CodigoPessoa - Recurso não encontrado no banco de dados!";
    public static final String CODIGOENDERECO_NAO_ENCONTRADO = "CodigoEndereco - Recurso não encontrado no banco de dados!";
    public static final String CODIGOENDERECO_INVALIDO_PRA_PESSOA_X = "CodigoEndereco - A Chave Identificadora não corresponde com a Pessoa." ;
    public static final String NOME_NAO_UNICO = "Nome - Recurso único já cadastrado no database.";
    public static final String NOME_NAO_UNICO_NO_MUNICIPIO = "Nome de Bairro - Recurso único já cadastrado no municípío.";
    public static final String NOME_NAO_UNICO_NA_UF = "Nome de Município - Recurso único já cadastrado na Unidade Federativa (UF)";
    public static final String SIGLA_NAO_UNICA = "Sigla - Recurso único já cadastrado no database.";
    public static final String LOGIN_NAO_UNICO = "Login - Recurso único já cadastrado no database.";
    public static final String RECURSO_NAO_ENCONTRADO = "Recurso não encontrado no banco de dados!";
}
