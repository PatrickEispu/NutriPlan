-- Tabela tipo_conta
CREATE TABLE tipo_conta (
    nr_id_tipo_conta SERIAL PRIMARY KEY,
    nm_tipo_conta VARCHAR(255) NOT NULL
);

INSERT INTO tipo_conta (nm_tipo_conta) VALUES ('ADMINISTRADOR');
INSERT INTO tipo_conta (nm_tipo_conta) VALUES ('MODERADOR');
INSERT INTO tipo_conta (nm_tipo_conta) VALUES ('CLIENTE');


-- Tabela conta
CREATE TABLE conta (
    nr_id_conta SERIAL PRIMARY KEY,
    nm_nome VARCHAR(255) NOT NULL,
    ds_email VARCHAR(255) NOT NULL,
    ds_senha VARCHAR(255) NOT NULL,
    fk_nr_id_tipo_conta INT NOT NULL,
    FOREIGN KEY (fk_nr_id_tipo_conta) REFERENCES tipo_conta(nr_id_tipo_conta)
);

-- Tabela categoria
CREATE TABLE categoria (
    nr_id_categoria SERIAL PRIMARY KEY,
    nm_categoria VARCHAR(255) NOT NULL
);

INSERT INTO categoria (nm_categoria) VALUES ('NAO_MUITO_ATIVO');
INSERT INTO categoria (nm_categoria) VALUES ('LEVEMENTE_ATIVO');
INSERT INTO categoria (nm_categoria) VALUES ('ATIVO');
INSERT INTO categoria (nm_categoria) VALUES ('BASTANTE_ATIVO');

-- Tabela cliente
CREATE TABLE cliente (
    fk_nr_id_conta SERIAL PRIMARY KEY,
    ds_genero VARCHAR(1),
    nr_peso DOUBLE PRECISION,
    nr_altura DOUBLE PRECISION,
    ds_data_nascimento VARCHAR(255),
    nr_tmb DOUBLE PRECISION,
    nr_get DOUBLE PRECISION,
    fk_nr_id_categoria INT,
    FOREIGN KEY (fk_nr_id_conta) REFERENCES conta(nr_id_conta),
    FOREIGN KEY (fk_nr_id_categoria) REFERENCES categoria(nr_id_categoria)
);


-- Tabela objetivo
CREATE TABLE objetivo (
    nr_id_objetivo SERIAL PRIMARY KEY,
    ds_objetivo VARCHAR(255)
);

INSERT INTO objetivo (ds_objetivo) VALUES ('PERDER_PESO');
INSERT INTO objetivo (ds_objetivo) VALUES ('MANUTENCAO');
INSERT INTO objetivo (ds_objetivo) VALUES ('HIPERTROFIA');


-- Tabela tempo
CREATE TABLE tempo (
    nr_id_tempo SERIAL PRIMARY KEY,
    ds_tempo VARCHAR(255)
);

INSERT INTO tempo (ds_tempo) VALUES ('RAPIDO');
INSERT INTO tempo (ds_tempo) VALUES ('MEDIO');
INSERT INTO tempo (ds_tempo) VALUES ('LONGO_PRAZO');

-- Tabela meta
CREATE TABLE meta (
    fk_nr_id_conta SERIAL PRIMARY KEY,
    nr_peso_desejado NUMERIC,
    fk_nr_id_objetivo INTEGER,
    fk_nr_id_tempo INTEGER,
    FOREIGN KEY (fk_nr_id_conta) REFERENCES conta(nr_id_conta),
    FOREIGN KEY (fk_nr_id_objetivo) REFERENCES objetivo(nr_id_objetivo),
    FOREIGN KEY (fk_nr_id_tempo) REFERENCES tempo(nr_id_tempo)
);

-- Tabela dispensa
CREATE TABLE dispensa (
    nr_id_dispensa SERIAL PRIMARY KEY,
    fk_nr_id_conta INT NOT NULL,
    FOREIGN KEY (fk_nr_id_conta) REFERENCES conta(nr_id_conta)
);

-- Tabela categoria_alimento
CREATE TABLE categoria_alimento (
    nr_id_categoria_alimento SERIAL PRIMARY KEY,
    nm_categoria VARCHAR(255) NOT NULL
);

INSERT INTO categoria_alimento (nm_categoria) VALUES ('CARBOIDRATO');
INSERT INTO categoria_alimento (nm_categoria) VALUES ('FIBRA');
INSERT INTO categoria_alimento (nm_categoria) VALUES ('GORDURA');
INSERT INTO categoria_alimento (nm_categoria) VALUES ('PROTEINA');


-- Tabela alimento
CREATE TABLE alimento (
    nr_id_alimento SERIAL PRIMARY KEY,
    fk_nr_id_categoria_alimento INT NOT NULL,
    nr_kcal DOUBLE PRECISION,
    nr_carboidrato DOUBLE PRECISION,
    nr_proteina DOUBLE PRECISION,
    nr_gordura DOUBLE PRECISION,
    nr_quantidade DOUBLE PRECISION,
    nm_nome VARCHAR(255) NOT NULL,
    FOREIGN KEY (fk_nr_id_categoria_alimento) REFERENCES categoria_alimento(nr_id_categoria_alimento)
);

-- Tabela dispensa_alimento
CREATE TABLE dispensa_alimento (
    fk_nr_id_dispensa INT NOT NULL,
    fk_nr_id_alimento INT NOT NULL,
    nr_quantidade INT,
    FOREIGN KEY (fk_nr_id_dispensa) REFERENCES dispensa(nr_id_dispensa),
    FOREIGN KEY (fk_nr_id_alimento) REFERENCES alimento(nr_id_alimento)
);

-- Tabela refeicao
CREATE TABLE refeicao (
    nr_id_refeicao SERIAL PRIMARY KEY,
    fk_nr_id_conta INT NOT NULL,
    FOREIGN KEY (fk_nr_id_conta) REFERENCES conta(nr_id_conta)
);

-- Tabela refeicao_alimento (intermediária)
CREATE TABLE refeicao_alimento (
    fk_nr_id_refeicao INT NOT NULL,
    fk_nr_id_alimento INT NOT NULL,
    nr_quantidade INT NOT NULL,
    FOREIGN KEY (fk_nr_id_refeicao) REFERENCES refeicao(nr_id_refeicao) ON DELETE CASCADE,
    FOREIGN KEY (fk_nr_id_alimento) REFERENCES alimento(nr_id_alimento)
);

-- Tabela diario
CREATE TABLE diario (
    fk_nr_id_conta INT NOT NULL,
    ds_data VARCHAR(255) NOT NULL,
    fk_nr_id_refeicao INT NOT NULL,
    FOREIGN KEY (fk_nr_id_conta) REFERENCES conta(nr_id_conta),
    FOREIGN KEY (fk_nr_id_refeicao) REFERENCES refeicao(nr_id_refeicao)
);

create table diario_meta (
	fk_nr_id_conta Integer not null,
	nr_kcal DOUBLE PRECISION,
    nr_carboidrato DOUBLE PRECISION,
    nr_proteina DOUBLE PRECISION,
    nr_gordura DOUBLE PRECISION,
    foreign key (fk_nr_id_conta) references conta (nr_id_conta)
   );