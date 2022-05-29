INSERT INTO tb_pessoa(nome, sobrenome, idade, login, senha, status)
    VALUES('Ludwig', 'Von Misses', 54, 'misses', 'misses123', 1);
INSERT INTO tb_pessoa(nome, sobrenome, idade, login, senha, status)
    VALUES('Olga', 'Weiss', 68, 'weiss', 'weiss123', 1);

INSERT INTO tb_uf(sigla, nome, status)
    VALUES('PR', 'Paraná', 1);
INSERT INTO tb_uf(sigla, nome, status)
    VALUES('RS', 'Rio Grande do Sul', 1);

INSERT INTO tb_municipio(nome, status, codigo_uf)
    VALUES('Cascavel', 1, 1);
INSERT INTO tb_municipio(nome, status, codigo_uf)
    VALUES('Porto Alegre', 1, 2);

INSERT INTO tb_bairro(nome, status)
    VALUES('Centro', 1);
INSERT INTO tb_bairro(nome, status)
    VALUES('Primavera', 1);
