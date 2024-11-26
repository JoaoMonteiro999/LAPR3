CREATE OR REPLACE PROCEDURE listarProdutosPorParcela(p_idParcela parcela.idParcela%TYPE, p_dataInicio cultura.dataInicio%TYPE, p_dataFim cultura.dataFim%TYPE) IS
    var_especie planta.especie%TYPE;
    var_nome tipoProduto.tipoProduto%TYPE;
    var_variedade planta.variedade%TYPE;

    listaEspecie SYS_REFCURSOR;
    listaFormatada SYS_REFCURSOR;

    BEGIN
        OPEN listaEspecie FOR
    		SELECT p.especie
            FROM operacaoAgricula op
            JOIN cultura c ON op.idCultura = c.idCultura
            JOIN planta p ON c.idPlanta = p.idPlanta
            JOIN tipoProduto tp ON p.idTipoProduto = tp.idTipoProduto
            WHERE op.idTipoOperacao = 7 AND op.idParcela = p_idParcela AND op.dataOperacao < p_dataFim AND op.dataOperacao > p_dataInicio
            GROUP BY p.especie;

        LOOP
        FETCH listaEspecie INTO var_especie;
        EXIT WHEN listaEspecie%notfound;

            dbms_output.put_line(var_especie);
			OPEN listaFormatada FOR
            SELECT tp.tipoProduto, p.variedade
            FROM operacaoAgricula op
            JOIN cultura c ON op.idCultura = c.idCultura
            JOIN planta p ON c.idPlanta = p.idPlanta
            JOIN tipoProduto tp ON p.idTipoProduto = tp.idTipoProduto
            WHERE op.idTipoOperacao = 7 AND op.idParcela = p_idParcela AND op.dataOperacao < p_dataFim AND op.dataOperacao > p_dataInicio AND p.especie = var_especie
            GROUP BY tp.tipoProduto, p.variedade;

            LOOP
            FETCH listaFormatada INTO var_nome, var_variedade;
            EXIT WHEN listaFormatada%notfound;

            dbms_output.put_line('	' || var_nome || ' ' || var_variedade);
            END LOOP;
        END LOOP;
    END;
/

BEGIN
    ListarProdutosPorParcela(108, TO_DATE('20-05-2023', 'DD-MM-YYYY'), TO_DATE('06-11-2023', 'DD-MM-YYYY'));
END;
