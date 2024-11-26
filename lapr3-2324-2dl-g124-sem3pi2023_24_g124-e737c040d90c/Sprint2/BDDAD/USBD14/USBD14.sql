create or replace FUNCTION listarParcelas RETURN SYS_REFCURSOR IS

  p_cursor SYS_REFCURSOR;
BEGIN
    OPEN p_cursor FOR
    SELECT DISTINCT p.idParcela, p.designacao, p.area
    FROM parcela p
    ORDER BY p.idParcela ASC;
    RETURN p_cursor;
END;
/

create or replace FUNCTION listarCulturas(p_idParcela cultura.idParcela%TYPE) RETURN SYS_REFCURSOR IS

  p_cursor SYS_REFCURSOR;
BEGIN
    OPEN p_cursor FOR
    SELECT DISTINCT c.idCultura, c.dataInicio, c.dataFim, plt.nome, plt.variedade
    FROM cultura c
    JOIN parcela p ON c.idParcela = p.idParcela
    JOIN planta plt ON c.idPlanta = plt.idPlanta
    WHERE c.idParcela = p_idParcela
    ORDER BY c.idCultura ASC;
    RETURN p_cursor;
END;
/


create or replace FUNCTION listarFatores RETURN SYS_REFCURSOR IS

  p_cursor SYS_REFCURSOR;
BEGIN
    OPEN p_cursor FOR
    SELECT DISTINCT f.idFatorDeProducao, f.nomeComercial
    FROM fatorDeProducao f
    ORDER BY f.idFatorDeProducao ASC;
    RETURN p_cursor;
END;
/

create or replace FUNCTION listarModos RETURN SYS_REFCURSOR IS

  p_cursor SYS_REFCURSOR;
BEGIN
    OPEN p_cursor FOR
    SELECT DISTINCT m.idModoDeAplicacao,m.modoDeAplicacao
    FROM modoDeAplicacao m
    ORDER BY m.idModoDeAplicacao ASC;
    RETURN p_cursor;
END;
/

create or replace FUNCTION listarUnidades RETURN SYS_REFCURSOR IS

    u_cursor SYS_REFCURSOR;
BEGIN
    OPEN u_cursor FOR
    SELECT DISTINCT u.idUnidade, u.unidade
    FROM unidade u
    ORDER BY u.idUnidade ASC;
    RETURN u_cursor;
END;
/





CREATE OR REPLACE FUNCTION getNextOperacaoId RETURN NUMBER IS id_ret NUMBER;
BEGIN
	SELECT MAX(idOperacao) + 1 INTO id_ret
	FROM operacaoAgricula;
RETURN id_ret;
END;
/

CREATE OR REPLACE FUNCTION parcelaValida(p_id parcela.idParcela%TYPE) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM parcela
    WHERE idParcela = p_id;
    RETURN ocorrencias > 0;
END;
/


CREATE OR REPLACE FUNCTION dataValida(dataOperacao operacaoAgricula.dataOperacao%TYPE, cultura_id IN cultura.idCultura%TYPE) RETURN BOOLEAN IS
    p_dataInicio cultura.dataInicio%TYPE;
    p_dataFim cultura.dataFim%TYPE;
BEGIN
    IF (cultura_id IS NULL) THEN
        RETURN (dataOperacao < CURRENT_DATE);
    END IF;

    SELECT dataInicio INTO p_dataInicio
    FROM cultura
    WHERE idCultura = cultura_id;

    SELECT dataFim INTO p_dataFim
    FROM cultura
    WHERE idCultura = cultura_id;
    RETURN (dataOperacao < CURRENT_DATE AND dataOperacao > p_dataInicio AND((p_dataFim IS NULL) OR (p_dataFim >= dataOperacao)));
END;
/


CREATE OR REPLACE FUNCTION area_valida(p_idParcela operacaoAgricula.idParcela%TYPE, p_area parcela.area%TYPE) RETURN BOOLEAN IS
    area_parcela parcela.area%TYPE;
BEGIN
SELECT area INTO area_parcela
FROM parcela
WHERE parcela.idParcela = p_idParcela;

RETURN (area_parcela >= p_area AND p_area > 0);
END;
/



CREATE OR REPLACE FUNCTION culturaValida(parcela_id IN cultura.idParcela%TYPE, cultura_id IN cultura.idCultura%TYPE) RETURN BOOLEAN IS
    ocorrencias INT;
BEGIN
    IF (cultura_id IS NULL) THEN
        RETURN TRUE;
    ELSE
        SELECT COUNT(*) INTO ocorrencias
        FROM cultura c
        WHERE idParcela = parcela_id AND idCultura = cultura_id;
        RETURN ocorrencias > 0;
    END IF;
END;
/


CREATE OR REPLACE FUNCTION fatorValida(p_idFator operacaoAgriculaComFator.idFatorDeProducao%TYPE) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM fatorDeProducao
    WHERE idFatorDeProducao = p_idFator;
    RETURN ocorrencias > 0;
END;
/

CREATE OR REPLACE FUNCTION modoValida(p_idModo modoDeAplicacao.idModoDeAplicacao%TYPE) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM modoDeAplicacao
    WHERE idModoDeAplicacao = p_idModo;
    RETURN ocorrencias > 0;
END;
/


CREATE OR REPLACE FUNCTION unidadeValida(p_idUnidade unidade.idUnidade%TYPE) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM unidade
    WHERE idUnidade = p_idUnidade;
    RETURN ocorrencias > 0;
END;
/



CREATE OR REPLACE PROCEDURE RegistarAplicacaoFator(
    p_idParcela operacaoAgricula.idParcela%TYPE,
    p_dataOperacao operacaoAgricula.dataOperacao%TYPE,
    p_idFatorDeProducao operacaoAgriculaComFator.idFatorDeProducao%TYPE,
    p_modo operacaoAgriculaComFator.idModoDeAplicacao%TYPE,
    p_area parcela.area%TYPE,
    p_quantidade operacaoAgricula.quantidade%TYPE,
    p_idUnidade operacaoAgricula.idUnidade%TYPE,
    p_idCultura cultura.idCultura%TYPE
) IS
    p_idOperacao operacaoAgricula.idOperacao%TYPE;
    error EXCEPTION;
	var_p_idCultura cultura.idCultura%TYPE;

BEGIN
    IF (p_idCultura = 0) THEN
  	var_p_idCultura := NULL;
	ELSE var_p_idCultura := p_idCultura;
	END IF;


    p_idOperacao := getNextOperacaoId();

    IF (
        parcelaValida(p_idParcela) AND
        culturaValida(p_idParcela, var_p_idCultura) AND
        fatorValida(p_idFatorDeProducao) AND
        modoValida(p_modo) AND
        area_valida(p_idParcela, p_area) AND
        (p_quantidade > 0) AND
        unidadeValida(p_idUnidade) AND
        dataValida(p_dataOperacao, var_p_idCultura)
    ) THEN
        INSERT INTO operacaoAgricula (idOperacao, dataOperacao, idParcela, idCultura, idTipoOperacao, quantidade, idUnidade)
        VALUES (p_idOperacao, p_dataOperacao, p_idParcela, var_p_idCultura, 4, p_quantidade, p_idUnidade);

        INSERT INTO operacaoAgriculaComFator (idOperacao, idFatorDeProducao, idModoDeAplicacao)
        VALUES (p_idOperacao, p_idFatorDeProducao, p_modo);
        COMMIT;
    ELSE
        RAISE error;
    END IF;


EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20004, 'Variáveis fornecidas inválidas');
END;
/



BEGIN
    RegistarAplicacaoFator(108,TO_DATE('6-10-2023', 'DD-MM-YYYY'),12,1,1.1,4000,3,null);
END;
/



