CREATE OR REPLACE FUNCTION listaFator(
    p_tipoOperacaoAgricula tipoOperacaoAgricula.tipoOperacao%TYPE,
    p_parcela parcela.idParcela%TYPE,
    p_dataInicio operacaoAgricula.dataOperacao%TYPE,
    p_dataFim operacaoAgricula.dataOperacao%TYPE
) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
BEGIN

OPEN lista FOR
SELECT op.dataOperacao, ft.nomeComercial, op.quantidade, un.unidade
FROM operacaoAgricula op
         JOIN operacaoAgriculaComFator opf ON op.idOperacao = opf.idOperacao
         JOIN fatorDeProducao ft ON opf.idFatorDeProducao = ft.idFatorDeProducao
         JOIN parcela p ON op.idParcela = p.idParcela
         JOIN unidade un ON op.idUnidade = un.idUnidade
    	 JOIN tipoOperacaoAgricula tipoOp ON op.idTipoOperacao = tipoOp.idTipoOperacao
WHERE op.dataOperacao BETWEEN p_dataInicio AND p_dataFim AND tipoOp.tipoOperacao = p_tipoOperacaoAgricula AND p_parcela = p.idParcela
ORDER BY op.dataOperacao ASC;
RETURN lista;
END;
/



CREATE OR REPLACE FUNCTION listaPorTipoDeOperacao(
    p_tipoOperacaoAgricula tipoOperacaoAgricula.tipoOperacao%TYPE,
    p_parcela parcela.idParcela%TYPE,
    p_dataInicio operacaoAgricula.dataOperacao%TYPE,
    p_dataFim operacaoAgricula.dataOperacao%TYPE
) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
BEGIN

OPEN lista FOR
SELECT op.dataOperacao, pl.nome, pl.variedade, op.quantidade, un.unidade
FROM operacaoAgricula op
         JOIN parcela p ON op.idParcela = p.idParcela
         JOIN cultura c ON op.idCultura = c.idCultura
         JOIN planta pl ON c.idPlanta = pl.idPlanta
         JOIN unidade un ON op.idUnidade = un.idUnidade
    	 JOIN tipoOperacaoAgricula tipoOp ON op.idTipoOperacao = tipoOp.idTipoOperacao
WHERE op.dataOperacao BETWEEN p_dataInicio AND p_dataFim AND tipoOp.tipoOperacao = p_tipoOperacaoAgricula AND p_parcela = p.idParcela
ORDER BY op.dataOperacao ASC;
RETURN lista;
END;
/




CREATE OR REPLACE FUNCTION listaMobilizacao(
    p_tipoOperacaoAgricula tipoOperacaoAgricula.tipoOperacao%TYPE,
    p_parcela parcela.idParcela%TYPE,
    p_dataInicio operacaoAgricula.dataOperacao%TYPE,
    p_dataFim operacaoAgricula.dataOperacao%TYPE
) RETURN SYS_REFCURSOR IS
    lista SYS_REFCURSOR;
BEGIN

OPEN lista FOR
SELECT op.dataOperacao, op.quantidade, un.unidade
FROM operacaoAgricula op
         JOIN parcela p ON op.idParcela = p.idParcela
         JOIN unidade un ON op.idUnidade = un.idUnidade
    	 JOIN tipoOperacaoAgricula tipoOp ON op.idTipoOperacao = tipoOp.idTipoOperacao
WHERE op.dataOperacao BETWEEN p_dataInicio AND p_dataFim AND tipoOp.tipoOperacao = p_tipoOperacaoAgricula AND p_parcela = p.idParcela
ORDER BY op.dataOperacao ASC;
RETURN lista;
END;
/

CREATE OR REPLACE PROCEDURE OperacaoPorTipoOperacao(
    p_parcela operacaoAgricula.idParcela%TYPE,
    dataInicio operacaoAgricula.dataOperacao%TYPE,
    dataFim operacaoAgricula.dataOperacao%TYPE
) IS
    operacaoAgriculaCursor SYS_REFCURSOR;
    tipoDeOperacaoCursor SYS_REFCURSOR;
    var_tipoOperacaoAgricula tipoOperacaoAgricula.tipoOperacao%TYPE;
    var_dataOperacao operacaoAgricula.dataOperacao%TYPE;
    var_nomeComercial fatorDeProducao.nomeComercial%TYPE;
    var_nome planta.nome%TYPE;
    var_variedade planta.variedade%TYPE;
    var_quantidade operacaoAgricula.quantidade%TYPE;
    var_unidade unidade.unidade%TYPE;
BEGIN
    OPEN operacaoAgriculaCursor FOR
    SELECT tipoOperacao
    FROM tipoOperacaoAgricula;

    LOOP
        FETCH operacaoAgriculaCursor INTO var_tipoOperacaoAgricula;
        EXIT WHEN operacaoAgriculaCursor%notfound;

        IF (var_tipoOperacaoAgricula = 'Fertilização' ) THEN
            tipoDeOperacaoCursor := listaFator(var_tipoOperacaoAgricula, p_parcela, dataInicio, dataFim);

            dbms_output.put_line('-------------------');
            dbms_output.put_line('Aplicação de fator de produção');
            dbms_output.put_line('');

            LOOP
                FETCH tipoDeOperacaoCursor INTO var_dataOperacao, var_nomeComercial, var_quantidade, var_unidade;
                EXIT WHEN tipoDeOperacaoCursor%notfound;
                dbms_output.put_line(var_dataOperacao || '||' || var_nomeComercial ||  '||' ||  var_quantidade || ' ' || var_unidade);
            END LOOP;
        ELSE
            IF  (var_tipoOperacaoAgricula = 'Mobilização do solo' ) THEN
				tipoDeOperacaoCursor := listaMobilizacao(var_tipoOperacaoAgricula, p_parcela, dataInicio, dataFim);

            	dbms_output.put_line('-------------------');
            	dbms_output.put_line(var_tipoOperacaoAgricula);
            	dbms_output.put_line('');

				LOOP
                FETCH tipoDeOperacaoCursor INTO var_dataOperacao, var_quantidade, var_unidade;
                EXIT WHEN tipoDeOperacaoCursor%notfound;
                dbms_output.put_line(var_dataOperacao || '||' || var_quantidade || ' ' || var_unidade);
            END LOOP;
			ELSE



                tipoDeOperacaoCursor := listaPorTipoDeOperacao(var_tipoOperacaoAgricula, p_parcela, dataInicio, dataFim);

                dbms_output.put_line('-------------------');
                dbms_output.put_line(var_tipoOperacaoAgricula);
                dbms_output.put_line('');

                LOOP
                    FETCH tipoDeOperacaoCursor INTO var_dataOperacao, var_nome, var_variedade, var_quantidade, var_unidade;
                    EXIT WHEN tipoDeOperacaoCursor%notfound;
                    dbms_output.put_line(var_dataOperacao || '||' || var_nome || ' ' || var_variedade || '||' || var_quantidade || ' ' || var_unidade);
            	END LOOP;

			END IF;
        END IF;

    END LOOP;
END;
/

BEGIN
OperacaoPorTipoOperacao(108,TO_DATE('01-07-2023', 'DD-MM-YYYY'), TO_DATE('02-10-2023', 'DD-MM-YYYY'));
END;