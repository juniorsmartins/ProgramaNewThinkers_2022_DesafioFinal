INSERT INTO tb_pessoa(nome, sobrenome, idade, login, senha, status)
    VALUES('Ludwig', 'Von Misses', 54, 'misses', 'misses123', 1);
INSERT INTO tb_pessoa(nome, sobrenome, idade, login, senha, status)
    VALUES('Olga', 'Weiss', 68, 'weiss', 'weiss123', 1);

INSERT INTO tb_uf(sigla, nome, status)
    VALUES('PR', 'Paraná', 1);
INSERT INTO tb_uf(sigla, nome, status)
    VALUES('RS', 'Rio Grande do Sul', 1);

INSERT INTO tb_municipio(nome, status)
    VALUES('Cascavel', 1);
INSERT INTO tb_municipio(nome, status)
    VALUES('Porto Alegre', 1);

INSERT INTO tb_bairro(nome, status)
    VALUES('Centro', 1);
INSERT INTO tb_bairro(nome, status)
    VALUES('Primavera', 1);

INSERT INTO tb_endereco(cep, nome_rua, numero, complemento, status)
    VALUES('78020-400', 'Rua Comandante Costa', 1550, 'Use interfone', 1);
INSERT INTO tb_endereco(cep, nome_rua, numero, complemento, status)
    VALUES('78020-400', 'Rua Outono Cinza', 2684, 'Entrada lateral', 1);

