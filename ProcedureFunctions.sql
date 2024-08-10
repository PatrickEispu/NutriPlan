-- PROCEDURE: public.atualizar_peso_cliente(character varying, double precision)

-- DROP PROCEDURE IF EXISTS public.atualizar_peso_cliente(character varying, double precision);

CREATE OR REPLACE PROCEDURE public.atualizar_peso_cliente(
	IN email_param character varying,
	IN novo_peso_param double precision)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    UPDATE clientes SET peso = novo_peso_param WHERE email = email_param;
END;
$BODY$;
ALTER PROCEDURE public.atualizar_peso_cliente(character varying, double precision)
    OWNER TO postgres;

-- PROCEDURE: public.criar_alimento(double precision, double precision, double precision, double precision, double precision, character varying)

-- DROP PROCEDURE IF EXISTS public.criar_alimento(double precision, double precision, double precision, double precision, double precision, character varying);

CREATE OR REPLACE PROCEDURE public.criar_alimento(
	IN kcal_param double precision,
	IN carboidrato_param double precision,
	IN proteina_param double precision,
	IN gordura_param double precision,
	IN quantidade_param double precision,
	IN nome_param character varying)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    INSERT INTO public.alimento (nome, kcal, carboidrato, proteina, gordura, quantidade)
    VALUES (nome_param, kcal_param, carboidrato_param, proteina_param, gordura_param, quantidade_param);
END;
$BODY$;
ALTER PROCEDURE public.criar_alimento(double precision, double precision, double precision, double precision, double precision, character varying)
    OWNER TO postgres;


-- PROCEDURE: public.criar_cliente(character varying, character varying, character, numeric, numeric, numeric, date, character varying, character varying, character varying)

-- DROP PROCEDURE IF EXISTS public.criar_cliente(character varying, character varying, character, numeric, numeric, numeric, date, character varying, character varying, character varying);

CREATE OR REPLACE PROCEDURE public.criar_cliente(
	IN nome character varying,
	IN email character varying,
	IN genero character,
	IN peso numeric,
	IN peso_desejado numeric,
	IN altura numeric,
	IN data_nascimento date,
	IN senha character varying,
	IN categoria character varying,
	IN tempo_meta character varying)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    INSERT INTO clientes (nome, email, genero, peso, peso_desejado, altura, data_nascimento, senha, categoria, tempo_meta)
    VALUES (nome, email, genero, peso, peso_desejado, altura, data_nascimento, senha, categoria, tempo_meta);
END;
$BODY$;
ALTER PROCEDURE public.criar_cliente(character varying, character varying, character, numeric, numeric, numeric, date, character varying, character varying, character varying)
    OWNER TO postgres;

-- PROCEDURE: public.formulario_objetivo(character varying, character varying, character varying)

-- DROP PROCEDURE IF EXISTS public.formulario_objetivo(character varying, character varying, character varying);

CREATE OR REPLACE PROCEDURE public.formulario_objetivo(
	IN email_param character varying,
	IN categoria_param character varying,
	IN tempo_meta_param character varying)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    UPDATE clientes 
    SET categoria = categoria_param, tempo_meta = tempo_meta_param 
    WHERE email = email_param;
END;
$BODY$;
ALTER PROCEDURE public.formulario_objetivo(character varying, character varying, character varying)
    OWNER TO postgres;


-- PROCEDURE: public.salvar_tmb_get(integer, double precision, double precision)

-- DROP PROCEDURE IF EXISTS public.salvar_tmb_get(integer, double precision, double precision);

CREATE OR REPLACE PROCEDURE public.salvar_tmb_get(
	IN cliente_id integer,
	IN tmb double precision,
	IN get double precision)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
    INSERT INTO public.calculo (cliente_id, tmb, get)
    VALUES (cliente_id, tmb, get);
END;
$BODY$;
ALTER PROCEDURE public.salvar_tmb_get(integer, double precision, double precision)
    OWNER TO postgres;


-- FUNCTION: public.listar_alimentos()

-- DROP FUNCTION IF EXISTS public.listar_alimentos();

CREATE OR REPLACE FUNCTION public.listar_alimentos(
	)
    RETURNS TABLE(kcal double precision, carboidrato double precision, proteina double precision, gordura double precision, quantidade double precision, nome character varying) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
BEGIN
    RETURN QUERY
    SELECT a.kcal, a.carboidrato, a.proteina, a.gordura, a.quantidade, a.nome
    FROM public.alimento a;
END;
$BODY$;

ALTER FUNCTION public.listar_alimentos()
    OWNER TO postgres;