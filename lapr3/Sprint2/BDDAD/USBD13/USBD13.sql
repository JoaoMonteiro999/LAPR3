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

CREATE OR REPLACE FUNCTION culturaValida(parcela_id IN cultura.idParcela%TYPE, cultura_id IN cultura.idCultura%TYPE) RETURN BOOLEAN IS
    ocorrencias INT;
BEGIN
    SELECT COUNT(*) INTO ocorrencias
    FROM cultura c
    WHERE idParcela = parcela_id AND idCultura = cultura_id;
    RETURN ocorrencias > 0;
END;
/

CREATE OR REPLACE FUNCTION dataValida(dataOperacao operacaoAgricula.dataOperacao%TYPE, cultura_id IN cultura.idCultura%TYPE) RETURN BOOLEAN IS
    p_dataInicio cultura.dataInicio%TYPE;
    p_dataFim cultura.dataFim%TYPE;
BEGIN  
    SELECT dataInicio INTO p_dataInicio
    FROM cultura
    WHERE idCultura = cultura_id;
    
    SELECT dataFim INTO p_dataFim
    FROM cultura
    WHERE idCultura = cultura_id;
    RETURN (dataOperacao < CURRENT_DATE AND dataOperacao > p_dataInicio AND((p_dataFim IS NULL) OR (p_dataFim >= dataOperacao)));
END;
/

CREATE OR REPLACE PROCEDURE RegistarColheita(p_idParcela operacaoAgricula.idParcela%TYPE, p_idCultura cultura.idPlanta%TYPE,
    p_dataOperacao operacaoAgricula.dataOperacao%TYPE, p_quantidade operacaoAgricula.quantidade%TYPE) IS

    p_idOperacao operacaoAgricula.idOperacao%TYPE;
    error EXCEPTION;
BEGIN
    p_idOperacao := getNextOperacaoId();

    IF (parcelaValida(p_idParcela) AND 
        culturaValida(p_idParcela,p_idCultura) AND
        dataValida(p_dataOperacao, p_idCultura) AND (p_quantidade > 0)
        ) THEN

        INSERT INTO operacaoAgricula (idOperacao, dataOperacao, idParcela, idCultura, idTipoOperacao, quantidade, idUnidade)
        VALUES (p_idOperacao, p_dataOperacao, p_idParcela, p_idCultura, 7, p_quantidade, 3);
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
    RegistarColheita(101,2,TO_DATE('30-05-2021', 'DD-MM-YYYY'), 7);
END;
/