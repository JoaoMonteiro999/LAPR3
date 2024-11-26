CREATE OR REPLACE FUNCTION listaPorTipoDeFator(
    p_tipoFatorProducao tipoFatorDeProducao.tipoFatorDeProducao%TYPE,
    p_dataInicio operacaoAgricula.dataOperacao%TYPE,
    p_dataFim operacaoAgricula.dataOperacao%TYPE
) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
BEGIN

OPEN lista FOR
SELECT op.dataOperacao, ft.nomeComercial, p.designacao, pl.nome, pl.variedade
FROM operacaoAgricula op
         JOIN operacaoAgriculaComFator opf ON op.idOperacao = opf.idOperacao
         JOIN fatorDeProducao ft ON opf.idFatorDeProducao = ft.idFatorDeProducao
         JOIN tipoFatorDeProducao tft ON ft.idTipoFatorDeProducao = tft.idTipoFatorDeProducao
         JOIN parcela p ON op.idParcela = p.idParcela
         JOIN cultura c ON op.idCultura = c.idCultura
         JOIN planta pl ON c.idPlanta = pl.idPlanta
WHERE op.dataOperacao BETWEEN p_dataInicio AND p_dataFim AND tft.tipoFatorDeProducao = p_tipoFatorProducao
ORDER BY op.dataOperacao ASC;
RETURN lista;
END;
/

CREATE OR REPLACE PROCEDURE OperacaoPorTipoFator(
    dataInicio operacaoAgricula.dataOperacao%TYPE,
    dataFim operacaoAgricula.dataOperacao%TYPE
) IS
    fatProdCursor SYS_REFCURSOR;
    tipoDeFatorOpsCursor SYS_REFCURSOR;
    var_tipoFatorProducao tipoFatorDeProducao.tipoFatorDeProducao%TYPE;
    var_dataOperacao operacaoAgricula.dataOperacao%TYPE;
    var_nomeComercial fatorDeProducao.nomeComercial%TYPE;
    var_designacao parcela.designacao%TYPE;
    var_nome planta.nome%TYPE;
    var_variedade planta.variedade%TYPE;
BEGIN
OPEN fatProdCursor FOR
SELECT tipoFatorDeProducao
FROM tipoFatorDeProducao;

LOOP
FETCH fatProdCursor INTO var_tipoFatorProducao;
        EXIT WHEN fatProdCursor%notfound;

        tipoDeFatorOpsCursor := listaPorTipoDeFator(var_tipoFatorProducao, dataInicio, dataFim);

		dbms_output.put_line('-------------------');
        dbms_output.put_line(var_tipoFatorProducao);
        dbms_output.put_line('');

        LOOP
FETCH tipoDeFatorOpsCursor INTO var_dataOperacao, var_nomeComercial, var_designacao, var_nome, var_variedade;
            EXIT WHEN tipoDeFatorOpsCursor%notfound;
            dbms_output.put_line(var_dataOperacao || '||' || var_nomeComercial || '||' || var_designacao || '||' || var_nome || '||' || var_variedade);
END LOOP;
END LOOP;
END;
/

BEGIN
OperacaoPorTipoFator(TO_DATE('30-04-2020', 'DD-MM-YYYY'), TO_DATE('30-04-2022', 'DD-MM-YYYY'));
END;