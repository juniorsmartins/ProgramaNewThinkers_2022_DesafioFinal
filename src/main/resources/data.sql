INSERT INTO tb_pessoa(nome, sobrenome, idade, login, senha, status)
    VALUES('Ludwig', 'Von Misses', 54, 'misses', 'misses123', 1);
INSERT INTO tb_pessoa(nome, sobrenome, idade, login, senha, status)
    VALUES('Olga', 'Weiss', 68, 'weiss', 'weiss123', 1);

INSERT INTO tb_uf(sigla, nome, status)
    VALUES('PR', 'Paraná', 1);
INSERT INTO tb_uf(sigla, nome, status)
    VALUES('RS', 'Rio Grande do Sul', 2);

INSERT INTO tb_municipio(nome, status, codigo_uf)
    VALUES('Cascavel', 1, 1);
INSERT INTO tb_municipio(nome, status, codigo_uf)
    VALUES('Porto Alegre', 2, 2);

INSERT INTO tb_bairro(nome, status, codigo_municipio)
    VALUES('Centro', 1, 2);
INSERT INTO tb_bairro(nome, status, codigo_municipio)
    VALUES('Primavera', 2, 1);

INSERT INTO tb_endereco(cep, nome_rua, numero, complemento, status, codigo_bairro, codigo_pessoa)
    VALUES('78020-400', 'Rua Comandante Costa', 1550, 'Use interfone', 1, 2, 1);
INSERT INTO tb_endereco(cep, nome_rua, numero, complemento, status, codigo_bairro, codigo_pessoa)
    VALUES('72551-110', 'Rua Outono Cinza', 2684, 'Entrada lateral', 2, 1, 2);

