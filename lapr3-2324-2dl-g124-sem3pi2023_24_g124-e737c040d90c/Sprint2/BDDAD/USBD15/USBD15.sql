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

create or replace FUNCTION listarCulturasPermanentes(p_idParcela cultura.idParcela%TYPE) RETURN SYS_REFCURSOR IS

  p_cursor SYS_REFCURSOR;
BEGIN
    OPEN p_cursor FOR
    SELECT DISTINCT c.idCultura, c.dataInicio, c.dataFim, plt.nome, plt.variedade
    FROM cultura c
    JOIN parcela p ON c.idParcela = p.idParcela
    JOIN planta plt ON c.idPlanta = plt.idPlanta
	JOIN tipoCultura tc ON plt.idTipoCultura = tc.idTipoCultura
    WHERE c.idParcela = p_idParcela AND plt.idTipoCultura = tc.idTipoCultura AND tc.idTipoCultura = 2
    ORDER BY c.idCultura ASC;
    RETURN p_cursor;
END;
/

CREATE OR REPLACE FUNCTION getNextOperacaoId RETURN NUMBER IS id_ret NUMBER;
BEGIN
	SELECT MAX(idOperacao) + 1 INTO id_ret
	FROM operacaoAgricula;
RETURN id_ret;
END;
/

CREATE OR REPLACE FUNCTION parcelaValida(p_id parcela.idParcela%type) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM parcela
    WHERE idParcela = p_id;
    RETURN ocorrencias > 0;
END;
/

CREATE OR REPLACE FUNCTION culturaPermanenteValida(parcela_id IN cultura.idParcela%TYPE, cultura_id IN cultura.idCultura%TYPE) RETURN BOOLEAN IS
    ocorrencias INT;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM cultura c
    JOIN planta plt ON c.idPlanta = plt.idPlanta
    JOIN tipoCultura tc ON plt.idTipoCultura = tc.idTipoCultura
    WHERE c.idParcela = parcela_id AND plt.idTipoCultura = tc.idTipoCultura AND tc.idTipoCultura = 2
    AND idCultura = cultura_id;
    RETURN ocorrencias > 0;
END;
/

CREATE OR REPLACE FUNCTION dataInicioValida(dataOperacao operacaoAgricula.dataOperacao%TYPE, cultura_id IN cultura.idCultura%TYPE) RETURN BOOLEAN IS
    p_dataInicio cultura.dataInicio%TYPE;
BEGIN
    SELECT dataInicio INTO p_dataInicio
    FROM cultura
    WHERE idCultura = cultura_id;

    RETURN (dataOperacao < CURRENT_DATE AND dataOperacao > p_dataInicio);
END;
/

CREATE OR REPLACE FUNCTION ValidarUnidPoda(p_idCultura cultura.idCultura%TYPE, p_quantidade cultura.quantidade%TYPE) RETURN BOOLEAN IS
     var_quantidade cultura.quantidade%TYPE;
BEGIN
    SELECT quantidade INTO var_quantidade
    FROM cultura
    WHERE cultura.idCultura = p_idCultura;

    RETURN (var_quantidade >= p_quantidade AND p_quantidade > 0);
END;
/

CREATE OR REPLACE PROCEDURE RegistarPoda(p_idParcela operacaoAgricula.idParcela%TYPE, p_idCultura cultura.idPlanta%TYPE,
    p_dataOperacao operacaoAgricula.dataOperacao%TYPE, p_quantidade operacaoAgricula.quantidade%TYPE) IS
    p_idOperacao operacaoAgricula.idOperacao%TYPE;
    error EXCEPTION;
BEGIN
    p_idOperacao := getNextOperacaoId();

    IF (parcelaValida(p_idParcela) AND
        culturaPermanenteValida(p_idParcela,p_idCultura) AND
        dataInicioValida(p_dataOperacao, p_idCultura) AND ValidarUnidPoda(p_idCultura, p_quantidade)
        ) THEN

        INSERT INTO operacaoAgricula (idOperacao, dataOperacao, idParcela, idCultura, idTipoOperacao, quantidade, idUnidade)
        VALUES (p_idOperacao, p_dataOperacao, p_idParcela, p_idCultura, 3, p_quantidade, 1);
        COMMIT;
    ELSE
        RAISE error;
    END IF;

EXCEPTION
    WHEN error THEN
        RAISE_APPLICATION_ERROR(-20001, 'Variáveis fornecidas inválidas');
END;
/

BEGIN
    RegistarPoda(102,11,TO_DATE('30-04-2021', 'DD-MM-YYYY'),  122);
END;
/