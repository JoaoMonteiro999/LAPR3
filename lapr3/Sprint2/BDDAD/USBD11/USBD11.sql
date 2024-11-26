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


create or replace FUNCTION listarPlantas RETURN SYS_REFCURSOR IS

    p_cursor SYS_REFCURSOR;
BEGIN
    OPEN p_cursor FOR
    SELECT DISTINCT p.idPlanta, p.nome, p.variedade, p.idTipoCultura
    FROM planta p
    ORDER BY p.idPlanta ASC;
    RETURN p_cursor;
END;
/

CREATE OR REPLACE FUNCTION getNextOperacaoId RETURN NUMBER IS

    id_ret NUMBER;
BEGIN
    SELECT MAX(idOperacao) + 1
    INTO id_ret
    FROM operacaoAgricula;
    RETURN id_ret;
END;
/


CREATE OR REPLACE FUNCTION getNextCulturaId RETURN NUMBER IS

    id_ret NUMBER;
BEGIN
    SELECT MAX(idCultura) + 1
    INTO id_ret
    FROM cultura;
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


CREATE OR REPLACE FUNCTION plantaValida(p_id planta.idPlanta%type) RETURN BOOLEAN IS

    ocorrencias NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO ocorrencias
    FROM planta
    WHERE idPlanta = p_id;
    RETURN ocorrencias > 0;
END;
/


CREATE OR REPLACE FUNCTION getTipoCultura(p_idPlanta cultura.idPlanta%TYPE) RETURN VARCHAR2 IS
    tp tipoCultura.tipoCultura%type;
BEGIN
    SELECT t.tipoCultura INTO tp
    FROM planta p
    JOIN tipoCultura t ON p.idTipoCultura = t.idTipoCultura
    WHERE p.idPlanta = p_idPlanta;
    RETURN tp;
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


CREATE OR REPLACE FUNCTION dataValida2(p_dataOperacao operacaoAgricula.dataOperacao%TYPE,p_dataFimCultura cultura.dataFim%TYPE) RETURN BOOLEAN IS
BEGIN
    RETURN ((p_dataFimCultura IS NULL OR p_dataFimCultura > p_dataOperacao) AND p_dataOperacao < CURRENT_DATE);
END;
/


CREATE OR REPLACE PROCEDURE RegistarSemeadura(p_idParcela operacaoAgricula.idParcela%TYPE,p_idPlanta cultura.idPlanta%TYPE,p_dataOperacao operacaoAgricula.dataOperacao%TYPE,p_dataFimCultura cultura.dataFim%TYPE,p_cQuantidade cultura.quantidade%TYPE,p_quantidade operacaoAgricula.quantidade%TYPE) IS

    p_idCultura operacaoAgricula.idCultura%TYPE;
    p_idOperacao operacaoAgricula.idOperacao%TYPE;
    error EXCEPTION;

BEGIN
    p_idCultura := getNextCulturaId();
    p_idOperacao := getNextOperacaoId();

    IF (parcelaValida(p_idParcela) AND plantaValida(p_idPlanta) AND area_valida(p_idParcela, p_cQuantidadep_idParcela) AND dataValida2(p_dataOperacao, p_dataFimCultura)) THEN

		IF (getTipoCultura(p_idPlanta) = 'Permanente') THEN
            INSERT INTO cultura (idCultura, idPlanta, idParcela, dataInicio, dataFim, quantidade, idUnidade) VALUES (p_idCultura, p_idPlanta, p_idParcela, p_dataOperacao, NULL, p_cQuantidade, 1);
        ELSE
            INSERT INTO cultura (idCultura, idPlanta, idParcela, dataInicio, dataFim, quantidade, idUnidade) VALUES (p_idCultura, p_idPlanta, p_idParcela, p_dataOperacao, p_dataFimCultura,  p_cQuantidade, 4);
        END IF;

        INSERT INTO operacaoAgricula (idOperacao, dataOperacao, idParcela, idCultura, idTipoOperacao, quantidade, idUnidade) VALUES (p_idOperacao, p_dataOperacao, p_idParcela, p_idCultura, 6, p_quantidade, 3);

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
    RegistarSemeadura(101,1,TO_DATE('30-04-2021', 'DD-MM-YYYY'),TO_DATE('30-04-2026', 'DD-MM-YYYY'),1.1,5.5);
END;
/


BEGIN
    RegistarColheita(101,2,TO_DATE('30-05-2021', 'DD-MM-YYYY'), 7);
	RegistarMonda(102,11,TO_DATE('5-10-2023', 'DD-MM-YYYY'), 2.5);
    RegistarSemeadura(101,1,TO_DATE('30-04-2021', 'DD-MM-YYYY'),TO_DATE('30-04-2026', 'DD-MM-YYYY'),1.1,5.5);
END;
/