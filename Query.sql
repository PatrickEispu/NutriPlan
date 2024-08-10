-- Table: public.clientes

-- DROP TABLE IF EXISTS public.clientes;

CREATE TABLE IF NOT EXISTS public.clientes
(
    id integer NOT NULL DEFAULT nextval('clientes_id_seq'::regclass),
    nome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    genero character(1) COLLATE pg_catalog."default",
    peso numeric(5,2),
    peso_desejado numeric(5,2),
    altura numeric(5,2),
    data_nascimento date,
    senha character varying(100) COLLATE pg_catalog."default" NOT NULL,
    categoria character varying(100) COLLATE pg_catalog."default",
    tempo_meta character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT clientes_pkey PRIMARY KEY (id),
    CONSTRAINT email_unico UNIQUE (email)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.clientes
    OWNER to postgres;

-- Table: public.calculo

-- DROP TABLE IF EXISTS public.calculo;

CREATE TABLE IF NOT EXISTS public.calculo
(
    id integer NOT NULL DEFAULT nextval('calculo_id_seq'::regclass),
    cliente_id integer NOT NULL,
    tmb double precision,
    get double precision,
    CONSTRAINT calculo_pkey PRIMARY KEY (id),
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id)
        REFERENCES public.clientes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.calculo
    OWNER to postgres;


-- Table: public.alimento

-- DROP TABLE IF EXISTS public.alimento;

CREATE TABLE IF NOT EXISTS public.alimento
(
    id integer NOT NULL DEFAULT nextval('alimento_id_seq'::regclass),
    kcal double precision NOT NULL,
    carboidrato double precision NOT NULL,
    proteina double precision NOT NULL,
    gordura double precision NOT NULL,
    quantidade double precision NOT NULL,
    nome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT alimento_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.alimento
    OWNER to postgres;

INSERT INTO alimentos (id, kcal, carboidrato, proteina, gordura, quantidade, nome) VALUES
(1, 130, 28, 0, 3, 100, 'Arroz Integral'),
(2, 159, 0, 32, 2, 100, 'Peito de frango'),
(3, 92, 24, 1, 0, 100, 'Banana nanica'),
(4, 76.5, 13, 5, 0.5, 100, 'Feijao'),
(5, 347, 56.7, 14.3, 7.3, 100, 'Aveia');


-- Table: public.diario

-- DROP TABLE IF EXISTS public.diario;

CREATE TABLE IF NOT EXISTS public.diario
(
    id integer NOT NULL DEFAULT nextval('diario_id_seq'::regclass),
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    alimento character varying(100) COLLATE pg_catalog."default" NOT NULL,
    quantidade integer NOT NULL,
    kcal double precision NOT NULL,
    carboidrato double precision NOT NULL,
    proteina double precision NOT NULL,
    gordura double precision NOT NULL,
    data date NOT NULL,
    CONSTRAINT diario_pkey PRIMARY KEY (id),
    CONSTRAINT fk_cliente FOREIGN KEY (email)
        REFERENCES public.clientes (email) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.diario
    OWNER to postgres;


